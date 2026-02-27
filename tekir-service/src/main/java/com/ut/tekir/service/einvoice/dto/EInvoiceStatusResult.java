package com.ut.tekir.service.einvoice.dto;

import com.ut.tekir.common.enums.EInvoiceStatus;

/**
 * Result of checking an e-Invoice status.
 */
public record EInvoiceStatusResult(
        String uuid,
        EInvoiceStatus status,
        String statusDescription,
        String rawResponse) {
}
