package com.ut.tekir.repository;

import com.ut.tekir.common.entity.BankToAccountTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BankToAccountTransferRepository extends JpaRepository<BankToAccountTransfer, Long>, JpaSpecificationExecutor<BankToAccountTransfer> {
}
