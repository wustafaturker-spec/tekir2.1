package com.ut.tekir.repository;

import com.ut.tekir.common.entity.InvoiceCurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceCurrencyRateRepository extends JpaRepository<InvoiceCurrencyRate, Long>, JpaSpecificationExecutor<InvoiceCurrencyRate> {
}
