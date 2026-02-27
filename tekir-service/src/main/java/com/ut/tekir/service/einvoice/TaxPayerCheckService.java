package com.ut.tekir.service.einvoice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ut.tekir.common.entity.Contact;
import com.ut.tekir.common.entity.EInvoiceSettings;
import com.ut.tekir.common.enums.EDocumentType;
import com.ut.tekir.common.enums.InvoiceScenario;
import com.ut.tekir.repository.ContactRepository;
import com.ut.tekir.service.einvoice.dto.GibTaxPayerInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service for automatic VKN/TCKN taxpayer checking.
 * Updates Contact's e-Invoice registration fields.
 * 
 * Caching: Will not re-query the same VKN within 24 hours
 * unless explicitly forced (bypass cache).
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TaxPayerCheckService {

    private static final long CACHE_HOURS = 24;

    private final EInvoiceSettingsService settingsService;
    private final EInvoiceProviderFactory providerFactory;
    private final ContactRepository contactRepository;

    /**
     * Check if a contact is an e-Invoice taxpayer and update their record.
     * Respects 24h cache — will skip check if recently verified.
     * 
     * @return true if the contact is a registered e-Invoice taxpayer
     */
    @Transactional
    public boolean checkAndUpdateContact(Contact contact) {
        return checkAndUpdateContact(contact, false);
    }

    /**
     * Check and update contact, optionally bypassing the cache.
     */
    @Transactional
    public boolean checkAndUpdateContact(Contact contact, boolean forceRefresh) {
        // Skip if no tax number
        if (!StringUtils.hasText(contact.getTaxNumber())) {
            log.debug("Contact {} has no tax number, skipping e-Invoice check", contact.getId());
            return false;
        }

        // Check cache (24h)
        if (!forceRefresh && contact.getEInvoiceCheckedAt() != null) {
            LocalDateTime cacheExpiry = contact.getEInvoiceCheckedAt().plusHours(CACHE_HOURS);
            if (LocalDateTime.now().isBefore(cacheExpiry)) {
                log.debug("Contact {} e-Invoice check is cached until {}", contact.getId(), cacheExpiry);
                return Boolean.TRUE.equals(contact.getEInvoiceRegistered());
            }
        }

        // Get tenant settings
        Optional<EInvoiceSettings> settingsOpt = settingsService.getSettings(contact.getTenantId());
        if (settingsOpt.isEmpty() || !Boolean.TRUE.equals(settingsOpt.get().getActive())) {
            log.debug("E-Invoice settings not configured or inactive for tenant {}", contact.getTenantId());
            return Boolean.TRUE.equals(contact.getEInvoiceRegistered());
        }

        EInvoiceSettings settings = settingsOpt.get();

        try {
            EInvoiceProviderService provider = providerFactory.getProvider(settings);
            GibTaxPayerInfo info = provider.checkTaxPayer(contact.getTaxNumber(), settings);

            // Update contact fields
            contact.setEInvoiceRegistered(info.registered());
            contact.setPkAlias(info.pkAlias());
            contact.setInvoiceScenario(info.scenario());
            contact.setGbAlias(info.gbAlias());
            contact.setEInvoiceCheckedAt(LocalDateTime.now());

            if (info.aliases() != null && !info.aliases().isEmpty()) {
                contact.setEInvoiceAliasList(String.join(",", info.aliases()));
            }

            contactRepository.save(contact);

            log.info("Contact {} VKN {} e-Invoice check: registered={}, scenario={}, pkAlias={}",
                    contact.getId(), contact.getTaxNumber(),
                    info.registered(), info.scenario(), info.pkAlias());

            return info.registered();

        } catch (Exception e) {
            log.warn("Failed to check e-Invoice status for contact {} (VKN: {}): {}",
                    contact.getId(), contact.getTaxNumber(), e.getMessage());
            // Update check timestamp even on failure to prevent hammering
            contact.setEInvoiceCheckedAt(LocalDateTime.now());
            contactRepository.save(contact);
            return Boolean.TRUE.equals(contact.getEInvoiceRegistered());
        }
    }

    /**
     * Get taxpayer info without updating DB.
     */
    public GibTaxPayerInfo getTaxPayerInfo(String vknTckn, String tenantId) {
        Optional<EInvoiceSettings> settingsOpt = settingsService.getSettings(tenantId);
        if (settingsOpt.isEmpty() || !Boolean.TRUE.equals(settingsOpt.get().getActive())) {
            return new GibTaxPayerInfo(false, vknTckn, null, null, List.of(), null, null, false);
        }

        try {
            EInvoiceProviderService provider = providerFactory.getProvider(settingsOpt.get());
            return provider.checkTaxPayer(vknTckn, settingsOpt.get());
        } catch (Exception e) {
            log.warn("Taxpayer check failed for VKN {}: {}", vknTckn, e.getMessage());
            return new GibTaxPayerInfo(false, vknTckn, null, null, List.of(), null, null, false);
        }
    }

    /**
     * Determine the e-Invoice document type for a given contact.
     * Returns EINVOICE if registered, EARCHIVE if not.
     */
    public EDocumentType determineDocumentType(Contact contact) {
        return Boolean.TRUE.equals(contact.getEInvoiceRegistered())
                ? EDocumentType.EINVOICE
                : EDocumentType.EARCHIVE;
    }
}
