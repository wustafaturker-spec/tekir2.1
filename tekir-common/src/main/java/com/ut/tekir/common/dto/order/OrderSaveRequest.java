package com.ut.tekir.common.dto.order;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public record OrderSaveRequest(
    Long id,
    @NotNull String tradeAction,
    @Size(max = 15) String serial,
    @Size(max = 30) String reference,
    LocalDate date,
    @NotNull Long contactId,
    Long warehouseId,
    String info,
    List<OrderItemSaveRequest> items
) {}
