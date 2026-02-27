package com.ut.tekir.common.dto.stockmovement;

import java.time.LocalDate;

public record StockMovementListDTO(
    Long id,
    String serial,
    String code,
    LocalDate date,
    String documentType,
    String fromWarehouseName,
    String toWarehouseName,
    String info
) {}
