package com.grace.elior.service;

import com.grace.api.model.AccountingDto;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AccountingService {

    Optional<AccountingDto> createAccounting(AccountingDto accountingDto);
    Optional<AccountingDto> updateAccounting(AccountingDto accountingDto);
    AccountingDto getAccounting(Long id);
    List<AccountingDto> getAllAccounting();
    List<AccountingDto> getAllAccounting(LocalDate startDate, LocalDate endDate);
    void deleteAccounting(Long id);

}
