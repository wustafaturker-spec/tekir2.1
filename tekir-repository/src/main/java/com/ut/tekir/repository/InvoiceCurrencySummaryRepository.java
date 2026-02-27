package com.ut.tekir.repository;

import com.ut.tekir.common.entity.InvoiceCurrencySummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceCurrencySummaryRepository extends JpaRepository<InvoiceCurrencySummary, Long>, JpaSpecificationExecutor<InvoiceCurrencySummary> {
}
