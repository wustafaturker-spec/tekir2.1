package com.ut.tekir.common.dto.payment;

import com.ut.tekir.common.dto.MoneySetDTO;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Full payment detail projection.
 */
public record PaymentDetailDTO(
    Long id,
    String code,
    LocalDate date,
    String paymentType,
    // Contact
    Long contactId,
    String contactCode,
    String contactFullname,
    // Account
    Long accountId,
    String accountName,
    // Financial
    MoneySetDTO totalAmount,
    // Items
    List<PaymentItemDTO> items,
    // Info
    String info,
    // Audit
    LocalDateTime createDate,
    String createUser,
    LocalDateTime updateDate
) {}
