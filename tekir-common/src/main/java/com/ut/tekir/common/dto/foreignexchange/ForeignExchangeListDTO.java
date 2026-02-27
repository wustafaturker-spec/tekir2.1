package com.ut.tekir.common.dto.foreignexchange;

import com.ut.tekir.common.dto.MoneySetDTO;
import java.time.LocalDate;

public record ForeignExchangeListDTO(
    Long id,
    String serial,
    String code,
    LocalDate date,
    MoneySetDTO fromAmount,
    MoneySetDTO toAmount,
    String info
) {}
