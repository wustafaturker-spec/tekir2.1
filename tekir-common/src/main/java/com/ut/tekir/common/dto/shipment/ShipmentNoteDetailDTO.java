package com.ut.tekir.common.dto.shipment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Full shipment note detail.
 */
public record ShipmentNoteDetailDTO(
    Long id,
    String code,
    String serial,
    String reference,
    LocalDate date,
    String tradeAction,
    Long contactId,
    String contactCode,
    String contactFullname,
    Long warehouseId,
    String warehouseName,
    List<ShipmentItemDTO> items,
    String info,
    LocalDateTime createDate,
    String createUser,
    LocalDateTime updateDate
) {}
