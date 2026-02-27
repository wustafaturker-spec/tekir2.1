package com.ut.tekir.tenant.dto;

import com.ut.tekir.tenant.entity.TenantStatus;

import java.time.LocalDateTime;

public record TenantListDTO(
    Long id,
    String name,
    String slug,
    TenantStatus status,
    Boolean active,
    Integer maxUsers,
    LocalDateTime createDate
) {}
