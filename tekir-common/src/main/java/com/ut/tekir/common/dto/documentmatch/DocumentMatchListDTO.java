package com.ut.tekir.common.dto.documentmatch;

import com.ut.tekir.common.dto.MoneySetDTO;

public record DocumentMatchListDTO(
    Long id,
    String sourceDocumentType,
    Long sourceDocumentId,
    String targetDocumentType,
    Long targetDocumentId,
    MoneySetDTO amount
) {}
