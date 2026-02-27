package com.ut.tekir.common.dto.contact;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.ut.tekir.common.enums.OpportunityStage;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ContactOpportunitySaveRequest(
    Long id,
    @NotNull Long contactId,
    @NotBlank @Size(max = 200) String title,
    @NotNull OpportunityStage stage,
    BigDecimal expectedRevenue,
    @Size(min = 3, max = 3) String currency,
    @Min(0) @Max(100) Integer probability,
    LocalDate expectedCloseDate,
    LocalDate actualCloseDate,
    String note,
    @Size(max = 100) String assignedTo,
    Boolean active
) {}
