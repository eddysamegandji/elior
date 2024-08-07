package com.grace.elior.mapper;

import com.grace.api.model.AccountingDto;
import com.grace.elior.model.Accounting;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountingMapper {

    AccountingDto toDto(Accounting accounting);

    Accounting toEntity(AccountingDto accountingDto);

}
