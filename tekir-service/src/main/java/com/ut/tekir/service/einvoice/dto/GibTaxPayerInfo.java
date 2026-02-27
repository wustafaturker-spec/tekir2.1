package com.ut.tekir.service.einvoice.dto;

import java.util.List;

import com.ut.tekir.common.enums.InvoiceScenario;

/**
 * Result of GİB taxpayer check.
 * Contains e-Invoice registration info for a VKN/TCKN.
 */
public record GibTaxPayerInfo(
        boolean registered,
        String vknTckn,
        String title,
        String pkAlias,
        List<String> aliases,
        InvoiceScenario scenario,
        String gbAlias,
        boolean edespatchRegistered) {
}
