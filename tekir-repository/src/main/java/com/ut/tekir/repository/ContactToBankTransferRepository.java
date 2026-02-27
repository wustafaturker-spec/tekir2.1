package com.ut.tekir.repository;

import com.ut.tekir.common.entity.ContactToBankTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactToBankTransferRepository extends JpaRepository<ContactToBankTransfer, Long>, JpaSpecificationExecutor<ContactToBankTransfer> {
}
