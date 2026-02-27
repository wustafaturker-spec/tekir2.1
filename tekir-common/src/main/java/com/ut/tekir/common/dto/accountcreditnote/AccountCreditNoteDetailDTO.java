package com.ut.tekir.common.dto.accountcreditnote;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AccountCreditNoteDetailDTO(
    Long id,
    String serial,
    String code,
    LocalDate date,
    Long accountId,
    String accountName,
    String info,
    String reference,
    LocalDateTime createDate,
    String createUser
) {}
