package com.ut.tekir.service.einvoice.dto;

import com.ut.tekir.common.enums.EInvoiceProvider;
import com.ut.tekir.common.enums.UblGenerationMode;

/**
 * DTO for e-Invoice settings save/update request.
 */
public record EInvoiceSettingsDTO(
        EInvoiceProvider providerType,
        String apiUrl,
        String apiKey,
        String apiSecret,
        String username,
        String password,
        String senderVkn,
        String senderAlias,
        Boolean active,
        Boolean einvoiceEnabled,
        Boolean earchiveEnabled,
        Boolean edespatchEnabled,
        Boolean eproducerEnabled,
        Boolean esmmEnabled,
        UblGenerationMode ublGenerationMode,
        String defaultTemplate) {
}
