package com.ut.tekir.common.dto.portfolio;

import java.time.LocalDateTime;

public record PortfolioDetailDTO(
    Long id,
    String code,
    String name,
    Boolean active,
    String info,
    LocalDateTime createDate,
    String createUser
) {}
