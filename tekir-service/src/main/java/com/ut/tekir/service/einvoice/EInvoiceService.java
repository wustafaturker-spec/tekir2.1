package com.ut.tekir.service.einvoice;

import com.ut.tekir.common.entity.Contact;
import com.ut.tekir.common.entity.EInvoiceLog;
import com.ut.tekir.common.entity.EInvoiceSettings;
import com.ut.tekir.common.entity.Invoice;
import com.ut.tekir.common.enums.EDocumentType;
import com.ut.tekir.common.enums.EInvoiceDirection;
import com.ut.tekir.common.enums.EInvoiceStatus;
import com.ut.tekir.repository.EInvoiceLogRepository;
import com.ut.tekir.repository.InvoiceRepository;
import com.ut.tekir.service.einvoice.dto.EInvoiceSendResult;
import com.ut.tekir.service.einvoice.dto.EInvoiceStatusResult;
import com.ut.tekir.service.einvoice.ubl.UblTrGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class EInvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final EInvoiceSettingsService settingsService;
    private final EInvoiceProviderFactory providerFactory;
    private final UblTrGenerator ublTrGenerator;
    private final EInvoiceLogRepository logRepository;

    @Transactional
    public EInvoiceSendResult sendInvoice(Long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Fatura bulunamadı: " + invoiceId));

        if (invoice.getEInvoiceStatus() == EInvoiceStatus.SENT
                || invoice.getEInvoiceStatus() == EInvoiceStatus.ACCEPTED) {
            throw new RuntimeException("Fatura zaten gönderilmiş veya onaylanmış durumda.");
        }

        EInvoiceSettings settings = settingsService.getSettings(invoice.getTenantId())
                .orElseThrow(() -> new RuntimeException("E-Fatura ayarları bulunamadı. Lütfen önce ayarları yapın."));

        if (!settings.getActive()) {
            throw new RuntimeException("E-Fatura entegrasyonu aktif değil.");
        }

        // Determine document type
        EDocumentType documentType = invoice.getEInvoiceType();
        if (documentType == null) {
            documentType = determineDocumentType(invoice.getContact());
        }

        // Generate UUID if not exists
        if (invoice.getEInvoiceUuid() == null) {
            invoice.setEInvoiceUuid(UUID.randomUUID().toString());
        }

        Contact contact = invoice.getContact();
        byte[] ublXml = ublTrGenerator.generateUbl(invoice, contact, settings, documentType);

        EInvoiceProviderService providerService = providerFactory.getProvider(settings);
        EInvoiceSendResult result = providerService.sendInvoice(invoice, ublXml, settings);

        // Create log
        EInvoiceLog einvoiceLog = new EInvoiceLog();
        einvoiceLog.setInvoice(invoice);
        einvoiceLog.setDirection(EInvoiceDirection.OUTBOUND);
        einvoiceLog.setDocumentType(documentType);
        einvoiceLog.setUuid(invoice.getEInvoiceUuid());
        einvoiceLog.setSentAt(LocalDateTime.now());
        einvoiceLog.setStatus(result.success() ? EInvoiceStatus.SENT : EInvoiceStatus.ERROR);
        einvoiceLog.setProviderResponse(result.message());
        einvoiceLog.setErrorMessage(result.message()); // or however we want to handle message
        // TODO: Store UBL XML if needed in future (requires column addition)
        logRepository.save(einvoiceLog);

        // Update invoice
        invoice.setEInvoiceStatus(einvoiceLog.getStatus());
        invoiceRepository.save(invoice);

        return result;
    }

    @Transactional
    public EInvoiceStatusResult checkStatus(Long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Fatura bulunamadı."));

        if (invoice.getEInvoiceUuid() == null) {
            throw new RuntimeException("Faturanın e-belge UUID'si bulunmuyor.");
        }

        EInvoiceSettings settings = settingsService.getSettings(invoice.getTenantId())
                .orElseThrow(() -> new RuntimeException("Ayarlar bulunamadı."));

        EInvoiceProviderService providerService = providerFactory.getProvider(settings);
        EInvoiceStatusResult result = providerService.checkStatus(invoice.getEInvoiceUuid(), settings);

        if (result.status() != null) {
            invoice.setEInvoiceStatus(result.status());
            invoiceRepository.save(invoice);

            // Update last log
            logRepository.findFirstByInvoiceIdOrderBySentAtDesc(invoiceId).ifPresent(l -> {
                l.setStatus(result.status());
                l.setProviderResponse(result.statusDescription());
                logRepository.save(l);
            });
        }

        return result;
    }

    @Transactional(readOnly = true)
    public byte[] getInvoicePdf(Long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Fatura bulunamadı."));

        if (invoice.getEInvoiceUuid() == null) {
            throw new RuntimeException("Faturanın e-belge UUID'si bulunmuyor.");
        }

        EInvoiceSettings settings = settingsService.getSettings(invoice.getTenantId())
                .orElseThrow(() -> new RuntimeException("Ayarlar bulunamadı."));

        EInvoiceProviderService providerService = providerFactory.getProvider(settings);
        return providerService.getInvoicePdf(invoice.getEInvoiceUuid(), settings);
    }

    @Transactional
    public java.util.List<com.ut.tekir.service.einvoice.dto.InboundInvoiceDTO> fetchInboundInvoices(String tenantId,
            java.time.LocalDate from, java.time.LocalDate to) {
        EInvoiceSettings settings = settingsService.getSettings(tenantId)
                .orElseThrow(() -> new RuntimeException("Ayarlar bulunamadı."));

        EInvoiceProviderService providerService = providerFactory.getProvider(settings);
        java.util.List<com.ut.tekir.service.einvoice.dto.InboundInvoiceDTO> invoices = providerService
                .fetchInboundInvoices(settings, from, to);

        // TODO: Save to database (EInvoiceLog/Invoice) if not already exists
        return invoices;
    }

    @Transactional
    public void sendResponse(String tenantId, String uuid, boolean accept, String note) {
        EInvoiceSettings settings = settingsService.getSettings(tenantId)
                .orElseThrow(() -> new RuntimeException("Ayarlar bulunamadı."));

        EInvoiceProviderService providerService = providerFactory.getProvider(settings);
        providerService.sendResponse(uuid, accept, note, settings);

        // TODO: Update local status
    }

    private EDocumentType determineDocumentType(Contact contact) {
        return (contact.getEInvoiceRegistered() != null && contact.getEInvoiceRegistered())
                ? EDocumentType.EINVOICE
                : EDocumentType.EARCHIVE;
    }
}
