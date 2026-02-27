package com.ut.tekir.common.dto.portfolio;

public record PortfolioListDTO(
    Long id,
    String code,
    String name,
    Boolean active,
    String info
) {}
