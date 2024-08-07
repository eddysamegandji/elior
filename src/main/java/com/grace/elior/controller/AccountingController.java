package com.grace.elior.controller;

import com.grace.api.handler.AccountingResourceApi;
import com.grace.api.model.AccountingDto;
import com.grace.elior.service.AccountingService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.internalServerError;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
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

}
