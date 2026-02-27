package com.ut.tekir.common.dto.promissory;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Promissory note list DTO for paginated views.
 */
public record PromissoryNoteListDTO(
    Long id,
    String code,
    String promissoryOwner,
    String status,
    BigDecimal amount,
    String currency,
    LocalDate dueDate,
    String contactFullname,
    String direction,
    String promissoryNumber,
    String info
) {}
