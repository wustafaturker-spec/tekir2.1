package com.ut.tekir.repository;

import com.ut.tekir.common.entity.PosCardList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PosCardListRepository extends JpaRepository<PosCardList, Long>, JpaSpecificationExecutor<PosCardList> {
}
