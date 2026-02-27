package com.ut.tekir.common.dto.debitcredit;

import java.time.LocalDate;

public record DebitCreditNoteListDTO(
    Long id,
    String serial,
    String code,
    LocalDate date,
    String action,
    String contactName,
    String info
) {}
