package com.ut.tekir.repository;

import com.ut.tekir.common.entity.PosCommisionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PosCommisionDetailRepository extends JpaRepository<PosCommisionDetail, Long>, JpaSpecificationExecutor<PosCommisionDetail> {
}
