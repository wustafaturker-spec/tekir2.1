package com.ut.tekir.repository;

import com.ut.tekir.common.entity.FoundationTxn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FoundationTxnRepository extends JpaRepository<FoundationTxn, Long>, JpaSpecificationExecutor<FoundationTxn> {
}
