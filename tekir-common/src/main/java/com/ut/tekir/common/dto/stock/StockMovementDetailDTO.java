package com.ut.tekir.common.dto.stock;

import java.math.BigDecimal;
import java.time.LocalDate;

public record StockMovementDetailDTO(
        Long id,
        LocalDate date,
        String direction,
        String documentType,
        Long documentId,
        String documentCode,
        String contactName,
        String warehouseName,
        BigDecimal quantity,
        String unit,
        BigDecimal unitPrice,
        String reference,
        String info
) {}
