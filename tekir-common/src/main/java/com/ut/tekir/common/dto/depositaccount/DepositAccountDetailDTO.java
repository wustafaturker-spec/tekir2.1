package com.ut.tekir.common.dto.depositaccount;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record DepositAccountDetailDTO(
    Long id,
    String serial,
    String code,
    LocalDate date,
    Long bankAccountId,
    String bankAccountName,
    String info,
    String reference,
    LocalDateTime createDate,
    String createUser
) {}
