package com.grace.elior.service;

import com.grace.api.model.AccountingDto;
import java.util.List;
import java.util.Optional;

public interface AccountingService {

    Optional<AccountingDto> createAccounting(AccountingDto accountingDto);
    Optional<AccountingDto> updateAccounting(AccountingDto accountingDto);
    AccountingDto getAccounting(Long id);
    List<AccountingDto> getAllAccounting();
    void deleteAccounting(Long id);

}
