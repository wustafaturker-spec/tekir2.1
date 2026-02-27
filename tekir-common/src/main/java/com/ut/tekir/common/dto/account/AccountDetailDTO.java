package com.ut.tekir.common.dto.account;

import java.time.LocalDateTime;

/**
 * Full account detail DTO.
 */
public record AccountDetailDTO(
    Long id,
    String code,
    String name,
    String info,
    String accountType,
    String currency,
    Long organizationId,
    String organizationName,
    LocalDateTime createDate,
    String createUser,
    LocalDateTime updateDate
) {}
