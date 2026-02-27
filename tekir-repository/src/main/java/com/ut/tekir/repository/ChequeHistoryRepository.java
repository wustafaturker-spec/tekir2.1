package com.ut.tekir.repository;

import com.ut.tekir.common.entity.ChequeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChequeHistoryRepository extends JpaRepository<ChequeHistory, Long> {
}
