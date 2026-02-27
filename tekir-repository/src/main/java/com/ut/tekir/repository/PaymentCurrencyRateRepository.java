package com.ut.tekir.repository;

import com.ut.tekir.common.entity.PaymentCurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentCurrencyRateRepository extends JpaRepository<PaymentCurrencyRate, Long>, JpaSpecificationExecutor<PaymentCurrencyRate> {
}
