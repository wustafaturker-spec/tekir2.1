package com.ut.tekir.billing.repository;

import com.ut.tekir.billing.entity.PaymentRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PaymentRecordRepository extends JpaRepository<PaymentRecord, Long> {

    List<PaymentRecord> findByTenantId(Long tenantId);

    List<PaymentRecord> findBySubscriptionId(Long subscriptionId);

    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM PaymentRecord p WHERE p.status = 'COMPLETED'")
    BigDecimal sumCompletedPayments();

    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM PaymentRecord p WHERE p.tenantId = :tenantId AND p.status = 'COMPLETED'")
    BigDecimal sumCompletedPaymentsByTenant(Long tenantId);
}
