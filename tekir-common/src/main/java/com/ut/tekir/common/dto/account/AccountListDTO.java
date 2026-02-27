package com.ut.tekir.common.dto.account;

/**
 * Account list DTO for paginated views.
 */
public record AccountListDTO(
    Long id,
    String code,
    String name,
    String accountType,
    String currency,
    String organizationName
) {}
