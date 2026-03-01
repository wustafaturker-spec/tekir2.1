package com.ut.tekir.accounting.dto;

import com.ut.tekir.accounting.enums.AccountPlanType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AccountPlanDTO(
        Long id,
        String code,
        String name,
        String description,
        AccountPlanType planType,
        Boolean isDefault,
        Boolean isActive,
        LocalDate effectiveDate,
        Integer accountCount,
        LocalDateTime createDate,
        LocalDateTime updateDate
) {}
