package com.ut.tekir.repository;

import com.ut.tekir.common.entity.TaxTxn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxTxnRepository extends JpaRepository<TaxTxn, Long>, JpaSpecificationExecutor<TaxTxn> {
}
