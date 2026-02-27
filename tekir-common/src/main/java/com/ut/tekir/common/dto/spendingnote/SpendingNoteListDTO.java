package com.ut.tekir.common.dto.spendingnote;

import java.time.LocalDate;

public record SpendingNoteListDTO(
    Long id,
    String serial,
    String code,
    LocalDate date,
    String employeeName,
    String warehouseName,
    String info
) {}
