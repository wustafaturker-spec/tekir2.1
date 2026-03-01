package com.ut.tekir.accounting.dto;

import com.ut.tekir.accounting.enums.AccountPlanType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record AccountPlanSaveRequest(
        @NotBlank(message = "Plan kodu zorunludur")
        @Size(max = 20)
        String code,

        @NotBlank(message = "Plan adı zorunludur")
        @Size(max = 255)
        String name,

        String description,

        AccountPlanType planType,

        Boolean isDefault,

        LocalDate effectiveDate
) {}
