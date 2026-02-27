package com.ut.tekir.repository;
import com.ut.tekir.common.entity.TekirShipmentNoteCurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
@Repository public interface TekirShipmentNoteCurrencyRateRepository extends JpaRepository<TekirShipmentNoteCurrencyRate, Long>, JpaSpecificationExecutor<TekirShipmentNoteCurrencyRate> {}
