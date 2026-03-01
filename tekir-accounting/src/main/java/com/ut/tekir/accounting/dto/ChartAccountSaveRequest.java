package com.ut.tekir.accounting.dto;

import com.ut.tekir.accounting.enums.AccountType;
import com.ut.tekir.accounting.enums.NormalBalance;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ChartAccountSaveRequest(
        @NotBlank(message = "Hesap kodu zorunludur")
        @Pattern(regexp = "^\\d{1,20}$", message = "Hesap kodu yalnızca rakamlardan oluşmalıdır")
        @Size(max = 20)
        String code,

        @NotBlank(message = "Hesap adı zorunludur")
        @Size(max = 255)
        String name,

        String description,

        @NotNull(message = "Hesap türü zorunludur")
        AccountType accountType,

        @NotNull(message = "Normal bakiye zorunludur")
        NormalBalance normalBalance,

        Long parentId,

        Boolean isDetail,

        String currency,

        String taxCode
) {}
