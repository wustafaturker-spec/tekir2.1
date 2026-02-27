package com.ut.tekir.service.einvoice.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for an inbound (gelen) invoice fetched from provider.
 */
public record InboundInvoiceDTO(
        String uuid,
        String envelopeId,
        String senderVkn,
        String senderTitle,
        String invoiceNumber,
        LocalDate date,
        BigDecimal amount,
        String currency,
        String scenario,
        String documentType,
        String status) {
}
