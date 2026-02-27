package com.ut.tekir.service.einvoice;

import java.time.LocalDate;
import java.util.List;

import com.ut.tekir.common.entity.EInvoiceSettings;
import com.ut.tekir.common.entity.Invoice;
import com.ut.tekir.common.enums.EInvoiceProvider;
import com.ut.tekir.service.einvoice.dto.EInvoiceSendResult;
import com.ut.tekir.service.einvoice.dto.EInvoiceStatusResult;
import com.ut.tekir.service.einvoice.dto.GibTaxPayerInfo;
import com.ut.tekir.service.einvoice.dto.InboundInvoiceDTO;

/**
 * Strategy interface for e-Invoice provider integrations.
 * Each provider (Foriba, İzibiz, Uyumsoft, etc.) implements this interface.
 */
public interface EInvoiceProviderService {

    // ---- Outbound (Giden) ----

    /**
     * Send an invoice as e-Fatura or e-Arşiv.
     */
    EInvoiceSendResult sendInvoice(Invoice invoice, byte[] ublXml, EInvoiceSettings settings);

    /**
     * Check the processing status of a sent invoice.
     */
    EInvoiceStatusResult checkStatus(String uuid, EInvoiceSettings settings);

    /**
     * Download the PDF representation of a sent invoice.
     */
    byte[] getInvoicePdf(String uuid, EInvoiceSettings settings);

    /**
     * Cancel a previously sent invoice.
     */
    void cancelInvoice(String uuid, EInvoiceSettings settings);

    // ---- Inbound (Gelen) ----

    /**
     * Fetch inbound invoices from the provider within a date range.
     */
    List<InboundInvoiceDTO> fetchInboundInvoices(EInvoiceSettings settings, LocalDate from, LocalDate to);

    /**
     * Download the UBL-TR XML of an inbound invoice.
     */
    byte[] getInboundUbl(String uuid, EInvoiceSettings settings);

    /**
     * Send accept/reject response for an inbound commercial invoice.
     */
    void sendResponse(String uuid, boolean accept, String note, EInvoiceSettings settings);

    // ---- Taxpayer Check (Mükellef Sorgulama) ----

    /**
     * Check if a VKN/TCKN is registered as e-Invoice taxpayer.
     */
    GibTaxPayerInfo checkTaxPayer(String vknTckn, EInvoiceSettings settings);

    /**
     * Returns the provider type this implementation handles.
     */
    EInvoiceProvider getProviderType();
}
