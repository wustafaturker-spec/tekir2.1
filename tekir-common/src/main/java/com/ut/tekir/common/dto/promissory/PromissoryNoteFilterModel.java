package com.ut.tekir.common.dto.promissory;

import java.time.LocalDate;

/**
 * Filter parameters for promissory note search.
 */
public record PromissoryNoteFilterModel(
    String code,
    String promissoryOwner,
    String status,
    LocalDate dueDateFrom,
    LocalDate dueDateTo
) {}
