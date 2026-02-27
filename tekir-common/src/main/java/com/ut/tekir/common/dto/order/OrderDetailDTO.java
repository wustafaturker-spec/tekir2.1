package com.ut.tekir.common.dto.order;

import com.ut.tekir.common.dto.MoneySetDTO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Full order detail projection.
 */
public record OrderDetailDTO(
    Long id,
    String code,
    String serial,
    String reference,
    LocalDate date,
    String tradeAction,
    String orderStatus,
    // Contact
    Long contactId,
    String contactCode,
    String contactFullname,
    // Warehouse
    Long warehouseId,
    String warehouseName,
    // Financial
    MoneySetDTO grandTotal,
    // Items
    List<OrderItemDTO> items,
    // Info
    String info,
    // Audit
    LocalDateTime createDate,
    String createUser,
    LocalDateTime updateDate
) {}
