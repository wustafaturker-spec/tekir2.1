package com.ut.tekir.common.dto.accountcreditnote;

import java.time.LocalDate;

public record AccountCreditNoteListDTO(
    Long id,
    String serial,
    String code,
    LocalDate date,
    String accountName,
    String info
) {}
