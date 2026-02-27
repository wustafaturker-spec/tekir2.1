package com.ut.tekir.repository;
import com.ut.tekir.common.entity.TekirInvoiceTaxSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
@Repository public interface TekirInvoiceTaxSummaryRepository extends JpaRepository<TekirInvoiceTaxSummary, Long>, JpaSpecificationExecutor<TekirInvoiceTaxSummary> {}
