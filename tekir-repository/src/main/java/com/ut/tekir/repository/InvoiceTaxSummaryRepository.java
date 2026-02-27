package com.ut.tekir.repository;

import com.ut.tekir.common.entity.InvoiceTaxSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceTaxSummaryRepository extends JpaRepository<InvoiceTaxSummary, Long>, JpaSpecificationExecutor<InvoiceTaxSummary> {
}
