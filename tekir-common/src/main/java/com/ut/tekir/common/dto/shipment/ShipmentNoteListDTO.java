package com.ut.tekir.common.dto.shipment;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * ShipmentNote list DTO.
 */
public record ShipmentNoteListDTO(
    Long id,
    String code,
    String serial,
    String reference,
    LocalDate date,
    String tradeAction,
    String contactCode,
    String contactFullname,
    String warehouseName,
    String info
) {}
