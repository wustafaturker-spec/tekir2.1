package com.ut.tekir.service.einvoice.provider;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ut.tekir.common.entity.EInvoiceSettings;
import com.ut.tekir.common.entity.Invoice;
import com.ut.tekir.common.enums.EInvoiceProvider;
import com.ut.tekir.service.einvoice.EInvoiceProviderService;
import com.ut.tekir.service.einvoice.dto.EInvoiceSendResult;
import com.ut.tekir.service.einvoice.dto.EInvoiceStatusResult;
import com.ut.tekir.service.einvoice.dto.GibTaxPayerInfo;
import com.ut.tekir.service.einvoice.dto.InboundInvoiceDTO;

import lombok.extern.slf4j.Slf4j;

/**
 * Stub/placeholder provider for Foriba integration.
 * Will be replaced with actual Foriba API calls when credentials are available.
 * Currently returns safe defaults so the application can start.
 */
@Slf4j
@Service
public class ForibaProviderService implements EInvoiceProviderService {

    @Override
    public EInvoiceSendResult sendInvoice(Invoice invoice, byte[] ublXml, EInvoiceSettings settings) {
        log.warn("Foriba provider: sendInvoice not yet implemented");
        throw new UnsupportedOperationException("Foriba send not yet implemented");
    }

    @Override
    public EInvoiceStatusResult checkStatus(String uuid, EInvoiceSettings settings) {
        log.warn("Foriba provider: checkStatus not yet implemented");
        throw new UnsupportedOperationException("Foriba checkStatus not yet implemented");
    }

    @Override
    public byte[] getInvoicePdf(String uuid, EInvoiceSettings settings) {
        throw new UnsupportedOperationException("Foriba getInvoicePdf not yet implemented");
    }

    @Override
    public void cancelInvoice(String uuid, EInvoiceSettings settings) {
        throw new UnsupportedOperationException("Foriba cancelInvoice not yet implemented");
    }

    @Override
    public List<InboundInvoiceDTO> fetchInboundInvoices(EInvoiceSettings settings, LocalDate from, LocalDate to) {
        log.info("Foriba provider: Mocking fetchInboundInvoices between {} and {}", from, to);
        // Return dummy data for UI testing
        return List.of(
                new InboundInvoiceDTO(
                        java.util.UUID.randomUUID().toString(),
                        "ENV" + System.currentTimeMillis() + "1",
                        "1111111111",
                        "Örnek Tedarikçi A.Ş.",
                        "GIB2026000000001",
                        LocalDate.now().minusDays(1),
                        new java.math.BigDecimal("12500.50"),
                        "TRY",
                        "TICARIFATURA",
                        "SATIS",
                        "NEW"),
                new InboundInvoiceDTO(
                        java.util.UUID.randomUUID().toString(),
                        "ENV" + System.currentTimeMillis() + "2",
                        "2222222222",
                        "Test Hizmetleri Ltd. Şti.",
                        "GIB2026000000002",
                        LocalDate.now().minusDays(3),
                        new java.math.BigDecimal("4800.00"),
                        "TRY",
                        "TEMELFATURA",
                        "SATIS",
                        "NEW"));
    }

    @Override
    public byte[] getInboundUbl(String uuid, EInvoiceSettings settings) {
        throw new UnsupportedOperationException("Foriba getInboundUbl not yet implemented");
    }

    @Override
    public void sendResponse(String uuid, boolean accept, String note, EInvoiceSettings settings) {
        log.info("Foriba provider: Mocking sendResponse for UUID {}, accept: {}, note: {}", uuid, accept, note);
        // Just log the action since it's a mock
    }

    @Override
    public GibTaxPayerInfo checkTaxPayer(String vknTckn, EInvoiceSettings settings) {
        log.info("Foriba provider: checkTaxPayer for VKN {} (stub - returning not registered)", vknTckn);
        // Stub: return not registered. Will be replaced with actual API call.
        return new GibTaxPayerInfo(
                false, // registered
                vknTckn, // vknTckn
                null, // title
                null, // pkAlias
                List.of(), // aliases
                null, // scenario
                null, // gbAlias
                false // edespatchRegistered
        );
    }

    @Override
    public EInvoiceProvider getProviderType() {
        return EInvoiceProvider.FORIBA;
    }
}
