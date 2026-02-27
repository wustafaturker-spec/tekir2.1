package com.ut.tekir.common.dto.debitcredit;

import com.ut.tekir.common.dto.MoneySetDTO;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record DebitCreditNoteSaveRequest(
    @NotNull String action,
    Long contactId,
    String info,
    String reference,
    List<ItemRequest> items
) {
    public record ItemRequest(
        MoneySetDTO amount,
        String info,
        String lineCode
    ) {}
}
