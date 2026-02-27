package com.ut.tekir.common.dto.countnote;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record CountNoteDetailDTO(
    Long id,
    String serial,
    String code,
    LocalDate date,
    Long warehouseId,
    String warehouseName,
    Boolean approved,
    String info,
    String reference,
    List<CountNoteItemDTO> items,
    LocalDateTime createDate,
    String createUser
) {}
