package com.ut.tekir.common.dto.stock;

import java.math.BigDecimal;

public record StockStatusDTO(
        Long productId,
        String productCode,
        String productName,
        String unit,
        BigDecimal totalIn,
        BigDecimal totalOut,
        BigDecimal balance
) {}
