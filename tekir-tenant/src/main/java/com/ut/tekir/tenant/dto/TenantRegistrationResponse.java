package com.ut.tekir.tenant.dto;

import java.time.LocalDateTime;

public record TenantRegistrationResponse(
    Long tenantId,
    String slug,
    String accessToken,
    String refreshToken,
    LocalDateTime trialEndDate
) {}
