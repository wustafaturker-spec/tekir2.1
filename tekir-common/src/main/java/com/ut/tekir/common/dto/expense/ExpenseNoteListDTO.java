package com.ut.tekir.common.dto.expense;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * ExpenseNote list DTO.
 */
public record ExpenseNoteListDTO(
    Long id,
    String code,
    LocalDate date,
    String contactCode,
    String contactFullname,
    String accountName,
    String info
) {}
