package com.ut.tekir.repository;

import com.ut.tekir.common.entity.BankCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BankCardRepository extends JpaRepository<BankCard, Long>, JpaSpecificationExecutor<BankCard> {
}
