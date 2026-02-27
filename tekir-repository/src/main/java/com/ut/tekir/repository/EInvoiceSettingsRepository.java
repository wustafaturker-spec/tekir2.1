package com.ut.tekir.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ut.tekir.common.entity.EInvoiceSettings;

public interface EInvoiceSettingsRepository extends JpaRepository<EInvoiceSettings, Long> {
    Optional<EInvoiceSettings> findByTenantId(String tenantId);
}
