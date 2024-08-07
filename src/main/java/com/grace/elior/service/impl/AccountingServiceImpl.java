package com.grace.elior.service.impl;

import com.grace.api.model.AccountingDto;
import com.grace.elior.mapper.AccountingMapper;
import com.grace.elior.model.Accounting;
import com.grace.elior.repository.AccountingRepository;
import com.grace.elior.service.AccountingService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountingServiceImpl implements AccountingService {

    private final AccountingRepository accountingRepository;
    private final AccountingMapper accountingMapper;

    @Override
    public Optional<AccountingDto> createAccounting(AccountingDto accountingDto) {
        Accounting accounting = accountingMapper.toEntity(accountingDto);
        accounting.getArticles().forEach(a -> a.setAccounting(accounting));
        accounting.getExpenses().forEach(a -> a.setAccounting(accounting));
        Accounting savedAccounting = accountingRepository.save(accounting);
        return Optional.of(accountingMapper.toDto(savedAccounting));
    }

    @Override
    public Optional<AccountingDto> updateAccounting(AccountingDto accountingDto) {
        return Optional.empty();
    }

    @Override
    public AccountingDto getAccounting(Long id) {
        return null;
    }

    @Override
    public List<AccountingDto> getAllAccounting() {
        return accountingRepository.findAll().stream().map(accountingMapper::toDto).toList();
    }

    @Override
    public void deleteAccounting(Long id) {

    }
}
