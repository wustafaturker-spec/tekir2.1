package com.ut.tekir.common.dto.banktransfer;

import com.ut.tekir.common.dto.MoneySetDTO;
import java.time.LocalDate;

public record BankTransferListDTO(
    Long id,
    String serial,
    String code,
    LocalDate date,
    String transferType,
    MoneySetDTO fromAmount,
    MoneySetDTO toAmount,
    String info
) {}
