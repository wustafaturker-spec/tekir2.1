package com.ut.tekir.service.einvoice;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ut.tekir.common.entity.EInvoiceSettings;
import com.ut.tekir.common.exception.EntityNotFoundException;
import com.ut.tekir.repository.EInvoiceSettingsRepository;
import com.ut.tekir.service.einvoice.dto.EInvoiceSettingsDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service for managing e-Invoice settings per tenant.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EInvoiceSettingsService {

    private final EInvoiceSettingsRepository settingsRepository;
    private final EInvoiceProviderFactory providerFactory;

    /**
     * Get settings for the current tenant.
     */
    @Transactional(readOnly = true)
    public Optional<EInvoiceSettings> getSettings(String tenantId) {
        return settingsRepository.findByTenantId(tenantId);
    }

    /**
     * Get settings or throw if not configured.
     */
    @Transactional(readOnly = true)
    public EInvoiceSettings getSettingsOrThrow(String tenantId) {
        return settingsRepository.findByTenantId(tenantId)
                .orElseThrow(() -> new EntityNotFoundException("EInvoiceSettings", tenantId));
    }

    /**
     * Save or update e-Invoice settings.
     */
    @Transactional
    public EInvoiceSettings save(Long tenantId, EInvoiceSettingsDTO dto) {
        EInvoiceSettings settings = settingsRepository.findByTenantId(String.valueOf(tenantId))
                .orElseGet(EInvoiceSettings::new);

        settings.setProviderType(dto.providerType());
        settings.setApiUrl(dto.apiUrl());
        settings.setApiKey(dto.apiKey());
        if (dto.apiSecret() != null && !dto.apiSecret().isEmpty()) {
            settings.setApiSecret(dto.apiSecret());
        }
        settings.setUsername(dto.username());
        if (dto.password() != null && !dto.password().isEmpty()) {
            settings.setPassword(dto.password());
        }
        settings.setSenderVkn(dto.senderVkn());
        settings.setSenderAlias(dto.senderAlias());
        settings.setActive(dto.active());
        settings.setEinvoiceEnabled(dto.einvoiceEnabled());
        settings.setEarchiveEnabled(dto.earchiveEnabled());
        settings.setEdespatchEnabled(dto.edespatchEnabled());
        settings.setEproducerEnabled(dto.eproducerEnabled());
        settings.setEsmmEnabled(dto.esmmEnabled());
        settings.setUblGenerationMode(dto.ublGenerationMode());
        settings.setDefaultTemplate(dto.defaultTemplate());

        EInvoiceSettings saved = settingsRepository.save(settings);
        log.info("E-Invoice settings saved for tenant: {}", tenantId);
        return saved;
    }

    /**
     * Test connection to the configured provider.
     */
    @Transactional(readOnly = true)
    public boolean testConnection(Long tenantId) {
        EInvoiceSettings settings = getSettingsOrThrow(String.valueOf(tenantId));
        try {
            EInvoiceProviderService provider = providerFactory.getProvider(settings);
            // Try a simple taxpayer check as connection test
            provider.checkTaxPayer(settings.getSenderVkn(), settings);
            log.info("E-Invoice connection test successful for tenant: {}", tenantId);
            return true;
        } catch (Exception e) {
            log.warn("E-Invoice connection test failed for tenant {}: {}", tenantId, e.getMessage());
            return false;
        }
    }
}
