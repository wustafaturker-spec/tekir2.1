package com.ut.tekir.common.dto.expense;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Full expense note detail.
 */
public record ExpenseNoteDetailDTO(
    Long id,
    String code,
    LocalDate date,
    Long contactId,
    String contactCode,
    String contactFullname,
    Long accountId,
    String accountName,
    List<ExpenseItemDTO> items,
    String info,
    LocalDateTime createDate,
    String createUser,
    LocalDateTime updateDate
) {}
