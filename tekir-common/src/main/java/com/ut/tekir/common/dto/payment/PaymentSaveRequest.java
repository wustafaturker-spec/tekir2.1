package com.ut.tekir.common.dto.payment;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

/**
 * Payment create/update request DTO.
 */
public record PaymentSaveRequest(
    Long id,
    @NotNull String paymentType,
    LocalDate date,
    @NotNull Long contactId,
    Long accountId,
    String info,
    List<PaymentItemSaveRequest> items
) {}
