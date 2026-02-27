package com.ut.tekir.common.dto.interest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record InterestDetailDTO(
    Long id,
    String code,
    BigDecimal rate,
    String info,
    LocalDateTime createDate,
    String createUser
) {}
