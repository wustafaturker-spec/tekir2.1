package com.ut.tekir.common.dto.promissory;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Promissory note create/update request.
 */
public record PromissoryNoteSaveRequest(
    Long id,
    String promissoryOwner,
    @NotNull BigDecimal amount,
    @Size(max = 3) String currency,
    LocalDate maturityDate,
    String paymentPlace,
    String info,
    String direction, // Incoming, Outgoing
    Long contactId,
    String promissoryNumber
) {}
