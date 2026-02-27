package com.ut.tekir.repository;

import com.ut.tekir.common.entity.InvoiceOrderLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceOrderLinkRepository extends JpaRepository<InvoiceOrderLink, Long>, JpaSpecificationExecutor<InvoiceOrderLink> {
}
