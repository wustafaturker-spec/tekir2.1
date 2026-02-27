package com.ut.tekir.common.dto.promissory;

import com.ut.tekir.common.dto.MoneySetDTO;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Full promissory note detail projection.
 */
public record PromissoryNoteDetailDTO(
    Long id,
    String code,
    String status,
    String promissoryOwner,
    MoneySetDTO money,
    LocalDate dueDate,
    String paymentPlace,
    String info,
    LocalDateTime createDate,
    String createUser,
    LocalDateTime updateDate,
    String direction,
    String promissoryNumber,
    Long contactId,
    String contactCode,
    String contactFullname
) {}
