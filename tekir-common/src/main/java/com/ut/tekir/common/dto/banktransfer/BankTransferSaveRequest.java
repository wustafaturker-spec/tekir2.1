package com.ut.tekir.common.dto.banktransfer;

import com.ut.tekir.common.dto.MoneySetDTO;
import jakarta.validation.constraints.NotNull;

public record BankTransferSaveRequest(
    @NotNull String transferType,
    @NotNull MoneySetDTO fromAmount,
    @NotNull MoneySetDTO toAmount,
    MoneySetDTO cost,
    String info,
    String reference
) {}
