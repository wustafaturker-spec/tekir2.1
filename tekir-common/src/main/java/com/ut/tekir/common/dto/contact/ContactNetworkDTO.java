package com.ut.tekir.common.dto.contact;

/**
 * Contact network DTO — networkType string ile basitleştirildi.
 * networkType: EMAIL | WEB | TWITTER | FACEBOOK | SKYPE | OTHER
 */
public record ContactNetworkDTO(
    Long id,
    String info,
    String value,
    String networkType,
    Boolean activeNetwork,
    Boolean defaultNetwork
) {}
