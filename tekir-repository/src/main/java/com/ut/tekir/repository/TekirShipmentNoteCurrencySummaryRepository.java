package com.ut.tekir.repository;
import com.ut.tekir.common.entity.TekirShipmentNoteCurrencySummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
@Repository public interface TekirShipmentNoteCurrencySummaryRepository extends JpaRepository<TekirShipmentNoteCurrencySummary, Long>, JpaSpecificationExecutor<TekirShipmentNoteCurrencySummary> {}
