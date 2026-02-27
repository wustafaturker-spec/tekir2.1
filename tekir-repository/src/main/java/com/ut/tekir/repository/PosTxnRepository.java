package com.ut.tekir.repository;

import com.ut.tekir.common.entity.PosTxn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PosTxnRepository extends JpaRepository<PosTxn, Long>, JpaSpecificationExecutor<PosTxn> {
}
