package com.ut.tekir.common.dto.cheque;

import java.time.LocalDate;

/**
 * Filter parameters for cheque search.
 */
public record ChequeFilterModel(
    String code,
    String bankName,
    String chequeOwner,
    String status,
    LocalDate dueDateFrom,
    LocalDate dueDateTo
) {}
