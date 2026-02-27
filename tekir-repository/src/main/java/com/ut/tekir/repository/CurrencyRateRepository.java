package com.ut.tekir.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ut.tekir.common.entity.CurrencyPair;
import com.ut.tekir.common.entity.CurrencyRate;

@Repository
public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long> {
    List<CurrencyRate> findByCurrencyPairAndDate(CurrencyPair pair, LocalDate date);
    Optional<CurrencyRate> findFirstByCurrencyPairOrderByDateDesc(CurrencyPair pair);
}
