package com.ut.tekir.common.dto.accountcreditnote;

import jakarta.validation.constraints.NotNull;

public record AccountCreditNoteSaveRequest(
    @NotNull Long accountId,
    String info,
    String reference
) {}
