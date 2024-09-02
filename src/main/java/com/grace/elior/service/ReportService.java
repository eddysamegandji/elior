package com.grace.elior.service;

import com.grace.api.model.AccountingDto;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import org.springframework.core.io.ByteArrayResource;

public interface ReportService {

    ByteArrayResource generateAccountingReportWithRange(LocalDate startDate, LocalDate endDate);
}
