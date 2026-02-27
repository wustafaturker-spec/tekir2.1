package com.ut.tekir.common.dto.spendingnote;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record SpendingNoteDetailDTO(
    Long id,
    String serial,
    String code,
    LocalDate date,
    Long employeeId,
    String employeeName,
    Long warehouseId,
    String warehouseName,
    String action,
    String info,
    String reference,
    List<SpendingNoteItemDTO> items,
    LocalDateTime createDate,
    String createUser
) {}
