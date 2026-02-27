package com.ut.tekir.common.dto.portfolio;

import jakarta.validation.constraints.NotBlank;

public record PortfolioSaveRequest(
    @NotBlank String code,
    String name,
    Boolean active,
    String info
) {}
