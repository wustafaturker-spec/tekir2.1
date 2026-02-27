package com.ut.tekir.repository;

import com.ut.tekir.common.entity.InvoiceServiceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceServiceItemRepository extends JpaRepository<InvoiceServiceItem, Long>, JpaSpecificationExecutor<InvoiceServiceItem> {
}
