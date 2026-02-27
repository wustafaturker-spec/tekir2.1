package com.ut.tekir.common.dto.documentmatch;

import com.ut.tekir.common.dto.MoneySetDTO;
import jakarta.validation.constraints.NotNull;

public record DocumentMatchSaveRequest(
    @NotNull String sourceDocumentType,
    @NotNull Long sourceDocumentId,
    @NotNull String targetDocumentType,
    @NotNull Long targetDocumentId,
    @NotNull MoneySetDTO amount
) {}
