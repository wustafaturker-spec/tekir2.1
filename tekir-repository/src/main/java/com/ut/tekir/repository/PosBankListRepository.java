package com.ut.tekir.repository;

import com.ut.tekir.common.entity.PosBankList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PosBankListRepository extends JpaRepository<PosBankList, Long>, JpaSpecificationExecutor<PosBankList> {
}
