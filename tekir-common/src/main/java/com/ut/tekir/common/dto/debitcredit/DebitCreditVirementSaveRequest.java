package com.ut.tekir.common.dto.debitcredit;

import com.ut.tekir.common.dto.MoneySetDTO;
import java.util.List;

public record DebitCreditVirementSaveRequest(
    String info,
    String reference,
    List<ItemRequest> items
) {
    public record ItemRequest(
        MoneySetDTO amount,
        String info
    ) {}
}
