package com.ut.tekir.accounting.dto;

import com.ut.tekir.accounting.enums.AccountType;
import com.ut.tekir.accounting.enums.NormalBalance;

public record ChartAccountDTO(
        Long id,
        Long planId,
        String code,
        String name,
        String description,
        AccountType accountType,
        NormalBalance normalBalance,
        Integer level,
        Long parentId,
        String parentCode,
        Boolean isDetail,
        Boolean isActive,
        String currency,
        String taxCode,
        Boolean isBlocked
) {}
