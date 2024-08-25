package com.grace.elior.controller;

import com.grace.api.handler.AccountingResourceApi;
import com.grace.api.model.AccountingDto;
import com.grace.elior.service.AccountingService;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AccountingController implements AccountingResourceApi {

    private final AccountingService accountingService;

    @Override
    public ResponseEntity<AccountingDto> createAccounting(@RequestBody AccountingDto accountingDto) {
        if(accountingDto.getId() != null || accountingDto.getEmployeeName() == null || accountingDto.getEventDate() == null) {
            return badRequest().build();
        }
        Optional<AccountingDto> createdAccounting = accountingService.createAccounting(accountingDto);
        return createdAccounting
                .map(a -> ResponseEntity.status(HttpStatus.CREATED).body(a))
                .orElseGet(() -> internalServerError().build());
    }

    @Override
    public ResponseEntity<List<AccountingDto>> getAccountings() {
        List<AccountingDto> accountingList = accountingService.getAllAccounting();
        return accountingList.isEmpty() ? noContent().build() : ok(accountingList);
    }

    @Override
    public ResponseEntity<List<AccountingDto>> getAccountingsWithRange(Date startDate, Date endDate) {
        LocalDate startLocalDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endLocalDate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        List<AccountingDto> accountingList = accountingService.getAllAccounting(startLocalDate, endLocalDate);
        return accountingList.isEmpty() ? noContent().build() : ok(accountingList);
    }

    @Override
    public ResponseEntity<Void> deleteAccounting(Long id) {
        try {
            accountingService.deleteAccounting(id);
            return noContent().build();
        } catch (EmptyResultDataAccessException e) {
            log.error("Accounting not found while deleting");
            return notFound().build();
        } catch (Exception e) {
            log.error("Error while deleting accounting");
            return internalServerError().build();
        }
    }

}
