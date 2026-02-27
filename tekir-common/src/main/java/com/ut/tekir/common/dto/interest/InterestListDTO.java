package com.ut.tekir.common.dto.interest;

import java.math.BigDecimal;

public record InterestListDTO(
    Long id,
    String code,
    BigDecimal rate,
    String info
) {}
