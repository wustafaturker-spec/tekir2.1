package com.ut.tekir.common.dto.countnote;

import java.time.LocalDate;

public record CountNoteListDTO(
    Long id,
    String serial,
    String code,
    LocalDate date,
    String warehouseName,
    Boolean approved,
    String info
) {}
