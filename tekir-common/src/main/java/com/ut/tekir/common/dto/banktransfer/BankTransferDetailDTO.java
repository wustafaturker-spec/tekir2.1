package com.ut.tekir.common.dto.banktransfer;

import com.ut.tekir.common.dto.MoneySetDTO;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record BankTransferDetailDTO(
    Long id,
    String serial,
    String code,
    LocalDate date,
    String transferType,
    MoneySetDTO fromAmount,
    MoneySetDTO toAmount,
    MoneySetDTO cost,
    String info,
    String reference,
    LocalDateTime createDate,
    String createUser
) {}
