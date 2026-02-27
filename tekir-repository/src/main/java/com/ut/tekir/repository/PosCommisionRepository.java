package com.ut.tekir.repository;

import com.ut.tekir.common.entity.PosCommision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PosCommisionRepository extends JpaRepository<PosCommision, Long>, JpaSpecificationExecutor<PosCommision> {
}
