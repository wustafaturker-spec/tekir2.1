package com.ut.tekir.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ut.tekir.common.entity.CurrencyPair;

@Repository
public interface CurrencyPairRepository extends JpaRepository<CurrencyPair, Long> {
}
