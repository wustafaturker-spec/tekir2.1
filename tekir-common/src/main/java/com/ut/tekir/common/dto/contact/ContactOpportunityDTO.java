package com.ut.tekir.common.dto.contact;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ut.tekir.common.enums.OpportunityStage;

public record ContactOpportunityDTO(
    Long id,
    Long contactId,
    String title,
    OpportunityStage stage,
    BigDecimal expectedRevenue,
    String currency,
    Integer probability,
    LocalDate expectedCloseDate,
    LocalDate actualCloseDate,
    String note,
    String assignedTo,
    Boolean active,
    LocalDateTime createDate,
    String createUser
) {}
