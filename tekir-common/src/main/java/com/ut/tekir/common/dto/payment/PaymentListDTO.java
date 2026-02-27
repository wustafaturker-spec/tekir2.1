package com.ut.tekir.common.dto.payment;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Payment list DTO for paginated views.
 */
public record PaymentListDTO(
    Long id,
    String code,
    LocalDate date,
    String paymentType,
    String contactCode,
    String contactFullname,
    BigDecimal totalAmount,
    String currency,
    String info
) {}
