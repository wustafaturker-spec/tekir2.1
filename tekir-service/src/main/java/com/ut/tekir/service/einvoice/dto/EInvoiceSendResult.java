package com.ut.tekir.service.einvoice.dto;

import com.ut.tekir.common.enums.EInvoiceStatus;

/**
 * Result of sending an e-Invoice to a provider.
 */
public record EInvoiceSendResult(
        boolean success,
        String uuid,
        String envelopeId,
        EInvoiceStatus status,
        String message,
        String rawResponse) {
}
