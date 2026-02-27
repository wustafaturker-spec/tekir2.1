package com.ut.tekir.common.dto.account;

/**
 * Filter parameters for account search.
 */
public record AccountFilterModel(
    String code,
    String name,
    String accountType,
    String currency,
    Long organizationId
) {}
