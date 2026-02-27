package com.ut.tekir.common.dto.foreignexchange;

import com.ut.tekir.common.dto.MoneySetDTO;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record ForeignExchangeDetailDTO(
    Long id,
    String serial,
    String code,
    LocalDate date,
    Long fromBankAccountId,
    String fromBankAccountName,
    Long toBankAccountId,
    String toBankAccountName,
    MoneySetDTO fromAmount,
    MoneySetDTO toAmount,
    BigDecimal exchangeRate,
    String info,
    String reference,
    LocalDateTime createDate,
    String createUser
) {}
