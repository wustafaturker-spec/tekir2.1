package com.ut.tekir.common.dto.contact;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Ağ/dijital adres kayıt isteği.
 * networkType: EMAIL | WEB | TWITTER | FACEBOOK | SKYPE | LINKEDIN | OTHER
 */
public record ContactNetworkSaveRequest(
    Long id,
    @NotNull Long contactId,
    @NotBlank @Size(max = 100) String networkType,
    @NotBlank @Size(max = 500) String value,
    Boolean defaultNetwork,
    Boolean activeNetwork,
    String info
) {}
