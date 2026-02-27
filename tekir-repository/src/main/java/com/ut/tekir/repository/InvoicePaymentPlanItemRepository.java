package com.ut.tekir.repository;

import com.ut.tekir.common.entity.InvoicePaymentPlanItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoicePaymentPlanItemRepository extends JpaRepository<InvoicePaymentPlanItem, Long>, JpaSpecificationExecutor<InvoicePaymentPlanItem> {
}
