package com.ut.tekir.common.dto.shipment;

import java.math.BigDecimal;

/**
 * Shipment line item DTO.
 */
public record ShipmentItemDTO(
    Long id,
    Long productId,
    String productCode,
    String productName,
    BigDecimal quantity,
    String unitName,
    BigDecimal unitPrice,
    String currency,
    BigDecimal amount,
    BigDecimal taxRate,
    String info
) {}
