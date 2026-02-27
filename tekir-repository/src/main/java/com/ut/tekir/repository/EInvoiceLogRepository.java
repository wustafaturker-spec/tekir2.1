package com.ut.tekir.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ut.tekir.common.entity.EInvoiceLog;

public interface EInvoiceLogRepository extends JpaRepository<EInvoiceLog, Long> {
    List<EInvoiceLog> findByInvoiceIdOrderByCreateDateDesc(Long invoiceId);

    Optional<EInvoiceLog> findFirstByInvoiceIdOrderBySentAtDesc(Long invoiceId);

    Optional<EInvoiceLog> findByUuid(String uuid);

    Page<EInvoiceLog> findByTenantIdOrderByCreateDateDesc(Long tenantId, Pageable pageable);
}
