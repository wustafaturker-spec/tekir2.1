package com.ut.tekir.common.dto.foreignexchange;

import com.ut.tekir.common.dto.MoneySetDTO;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record ForeignExchangeSaveRequest(
    @NotNull Long fromBankAccountId,
    @NotNull Long toBankAccountId,
    @NotNull MoneySetDTO fromAmount,
    @NotNull MoneySetDTO toAmount,
    BigDecimal exchangeRate,
    String info,
    String reference
) {}
