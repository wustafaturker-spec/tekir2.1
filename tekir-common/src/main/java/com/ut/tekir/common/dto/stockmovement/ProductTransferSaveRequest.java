package com.ut.tekir.common.dto.stockmovement;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public record ProductTransferSaveRequest(
    @NotNull Long fromWarehouseId,
    @NotNull Long toWarehouseId,
    String info,
    String reference,
    List<ItemRequest> items
) {
    public record ItemRequest(
        @NotNull Long productId,
        @NotNull BigDecimal quantity,
        String info
    ) {}
}
