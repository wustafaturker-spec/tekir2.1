package com.ut.tekir.repository;
import com.ut.tekir.common.entity.TekirShipmentNoteTaxSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
@Repository public interface TekirShipmentNoteTaxSummaryRepository extends JpaRepository<TekirShipmentNoteTaxSummary, Long>, JpaSpecificationExecutor<TekirShipmentNoteTaxSummary> {}
