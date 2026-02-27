package com.ut.tekir.service.einvoice;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.ut.tekir.common.entity.EInvoiceSettings;
import com.ut.tekir.common.enums.EInvoiceProvider;

import lombok.extern.slf4j.Slf4j;

/**
 * Factory that selects the appropriate EInvoiceProviderService
 * based on tenant settings.
 */
@Slf4j
@Component
public class EInvoiceProviderFactory {

    private final Map<EInvoiceProvider, EInvoiceProviderService> providers;

    public EInvoiceProviderFactory(List<EInvoiceProviderService> providerServices) {
        this.providers = providerServices.stream()
                .collect(Collectors.toMap(EInvoiceProviderService::getProviderType, Function.identity()));
        log.info("Registered e-Invoice providers: {}", providers.keySet());
    }

    /**
     * Get the provider service for the given settings.
     * 
     * @throws IllegalStateException if no provider is configured or found
     */
    public EInvoiceProviderService getProvider(EInvoiceSettings settings) {
        if (settings == null || settings.getProviderType() == null) {
            throw new IllegalStateException("E-Invoice settings not configured");
        }
        EInvoiceProviderService provider = providers.get(settings.getProviderType());
        if (provider == null) {
            throw new IllegalStateException("No provider implementation found for: " + settings.getProviderType());
        }
        return provider;
    }

    /**
     * Check if a provider implementation is available.
     */
    public boolean isProviderAvailable(EInvoiceProvider providerType) {
        return providers.containsKey(providerType);
    }
}
