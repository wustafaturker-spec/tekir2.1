package com.ut.tekir.repository;

import com.ut.tekir.common.entity.ForeignExchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ForeignExchangeRepository extends JpaRepository<ForeignExchange, Long>, JpaSpecificationExecutor<ForeignExchange> {
}
