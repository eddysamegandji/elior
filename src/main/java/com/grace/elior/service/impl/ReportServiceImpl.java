package com.grace.elior.service.impl;

import com.grace.api.model.AccountingDto;
import com.grace.api.model.ArticleDto;
import com.grace.elior.mapper.AccountingMapper;
import com.grace.elior.model.Accounting;
import com.grace.elior.model.Article;
import com.grace.elior.repository.AccountingRepository;
import com.grace.elior.repository.ArticleRepository;
import com.grace.elior.service.ReportService;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.apache.poi.ss.util.CellUtil.createCell;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportServiceImpl implements ReportService {

    private final AccountingMapper accountingMapper;
    private final ArticleRepository articleRepository;
    private final AccountingRepository accountingRepository;

    @SneakyThrows
    @Override
    @Transactional
    public ByteArrayResource generateAccountingReportWithRange(LocalDate startDate, LocalDate endDate) {
        List<Article> articles = articleRepository.findByDateBetween(startDate, endDate);
        List<String> distinctProductNames = articles.stream()
                .map(article -> article.getProduct().getName()).distinct().toList();

        Double eventOrganizerCommissionTotal = accountingRepository.sumEventOrganizerCommissionWithinPeriod(startDate, endDate);

        Accounting accounting = new Accounting();

        for (String productName : distinctProductNames) {
            List<Article> filtredArticles = articles.stream().filter(a -> a.getProduct().getName().equals(productName)).toList();
            Article newArticle = new Article();
            newArticle.setProduct(filtredArticles.get(0).getProduct());
            newArticle.setQuantity(filtredArticles.stream().mapToInt(Article::getQuantity).sum());
            newArticle.setTotalArticlePurchasePrice(filtredArticles.stream().mapToDouble(Article::getTotalArticlePurchasePrice).sum());
            newArticle.setSellingPrice(filtredArticles.stream().mapToDouble(Article::getSellingPrice).sum());
            newArticle.setBenefit(filtredArticles.stream().mapToDouble(Article::getBenefit).sum());
            accounting.getArticles().add(newArticle);
        }
        accounting.setEventOrganizerCommission(eventOrganizerCommissionTotal);
        accounting.calculateTotal();

        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Rapport");
            Row row = null;
            Cell cell = null;
            int rowIndex = 0;

            //Create header row
            row = sheet.createRow(rowIndex);
            row.setHeightInPoints(20);
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
            Font font = workbook.createFont();
            font.setBold(true);
            headerCellStyle.setFont(font);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle.setWrapText(true);

            CellStyle totalStyle = workbook.createCellStyle();
            totalStyle.setAlignment(HorizontalAlignment.CENTER);
            totalStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            totalStyle.setFont(font);
            totalStyle.setWrapText(true);
            sheet.setColumnWidth(0, 400 * 20);

            createCell(row, 0, "Dénomination", headerCellStyle);
            createCell(row, 1, "Quantité", headerCellStyle);
            createCell(row, 2, "Prix d'achat", headerCellStyle);
            createCell(row, 3, "Prix de vente", headerCellStyle);
            createCell(row, 4, "Marge", headerCellStyle);
            rowIndex++;
            for (Article article : accounting.getArticles()) {
                row = sheet.createRow(rowIndex);
                row.setHeightInPoints(20);
                createCell(row, 0, article.getProduct().getName(), cellStyle);
                createCell(row, 1, String.valueOf(article.getQuantity()), cellStyle);
                createCell(row, 2, String.valueOf(article.getTotalArticlePurchasePrice()), cellStyle);
                createCell(row, 3, String.valueOf(article.getSellingPrice()), cellStyle);
                createCell(row, 4, String.valueOf(article.getBenefit()), cellStyle);
                rowIndex++;
            }
            row = sheet.createRow(rowIndex);
            row.setHeightInPoints(20);
            createCell(row, 0, "Sous-total", totalStyle);
            createCell(row, 2, String.valueOf(accounting.getTotalPurcharsePrice()), totalStyle);
            createCell(row, 3, String.valueOf(accounting.getTotalSellingPrice()), totalStyle);
            createCell(row, 4, String.valueOf(accounting.getTotalBenefit()), totalStyle);

            rowIndex++;
            row = sheet.createRow(rowIndex);
            row.setHeightInPoints(30);
            CellStyle expenseTotalStyle = workbook.createCellStyle();
            expenseTotalStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
            expenseTotalStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            expenseTotalStyle.setAlignment(HorizontalAlignment.CENTER);
            expenseTotalStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            expenseTotalStyle.setFont(font);
            createCell(row, 0, "Dépenses (Part organisateur soirée...)", cellStyle);
            createCell(row, 4, String.valueOf(accounting.getEventOrganizerCommission()), expenseTotalStyle);

            rowIndex ++;
            row = sheet.createRow(rowIndex);
            row.setHeightInPoints(30);
            createCell(row, 0, "Bénéfice brut (avant charges mensuelles)", cellStyle);
            createCell(row, 4, String.valueOf(accounting.getRealBenefit()), totalStyle);

            rowIndex ++;
            row = sheet.createRow(rowIndex);
            row.setHeightInPoints(30);
            createCell(row, 0, "Charges mensuelles", cellStyle);
            createCell(row, 4, String.valueOf(985000.0), expenseTotalStyle);

            rowIndex ++;
            row = sheet.createRow(rowIndex);
            row.setHeightInPoints(20);
            CellStyle realBenefitStyle = workbook.createCellStyle();
            realBenefitStyle.setFillForegroundColor(IndexedColors.SEA_GREEN.getIndex());
            realBenefitStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            realBenefitStyle.setAlignment(HorizontalAlignment.CENTER);
            realBenefitStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            realBenefitStyle.setFont(font);
            createCell(row, 0, "Bénéfice réel du mois", totalStyle);
            createCell(row, 4, String.valueOf(accounting.getRealBenefit() - 985000), realBenefitStyle);

            sheet.setAutoFilter(CellRangeAddress.valueOf("A1:E1"));
            for(int i = 1; i<5; i++) sheet.setColumnWidth(i, 400 * 10);

            ByteArrayResource resource;
            try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
                workbook.write(stream);
                resource = new ByteArrayResource(stream.toByteArray());
            }
            return resource;
        } catch (IOException e) {
            log.debug("GENERATION OF THE REPORT ACCOUNTING WITH RANGE EXITED WITH ERRORS");
            log.error(e.getMessage(), e);
            throw e;
        }
    }
}
