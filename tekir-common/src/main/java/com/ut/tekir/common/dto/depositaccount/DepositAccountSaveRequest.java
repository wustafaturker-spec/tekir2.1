package com.ut.tekir.common.dto.depositaccount;

import jakarta.validation.constraints.NotNull;

public record DepositAccountSaveRequest(
    @NotNull Long bankAccountId,
    String info,
    String reference
) {}
