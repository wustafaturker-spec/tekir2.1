package com.ut.tekir.common.dto.spendingnote;

import com.ut.tekir.common.dto.MoneySetDTO;
import java.math.BigDecimal;

public record SpendingNoteItemDTO(
    Long id,
    Long productId,
    String productName,
    BigDecimal quantity,
    MoneySetDTO unitPrice,
    MoneySetDTO amount,
    String lineCode,
    String info
) {}
