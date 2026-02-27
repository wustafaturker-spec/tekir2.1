package com.ut.tekir.tenant.dto;

import com.ut.tekir.tenant.entity.TenantStatus;

import java.time.LocalDateTime;

public record TenantDetailDTO(
    Long id,
    String name,
    String slug,
    String domain,
    TenantStatus status,
    String settings,
    String logoUrl,
    String taxNumber,
    String taxOffice,
    Integer maxUsers,
    Boolean active,
    LocalDateTime createDate,
    LocalDateTime updateDate
) {}
