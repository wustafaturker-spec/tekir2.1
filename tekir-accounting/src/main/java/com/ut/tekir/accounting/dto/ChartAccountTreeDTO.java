package com.ut.tekir.accounting.dto;

import com.ut.tekir.accounting.enums.AccountType;
import com.ut.tekir.accounting.enums.NormalBalance;

import java.util.List;

public record ChartAccountTreeDTO(
        Long id,
        String code,
        String name,
        AccountType accountType,
        NormalBalance normalBalance,
        Integer level,
        Long parentId,
        Boolean isDetail,
        Boolean isActive,
        Boolean isBlocked,
        String currency,
        List<ChartAccountTreeDTO> children
) {}
