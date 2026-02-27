package com.ut.tekir.repository;
import com.ut.tekir.common.entity.TekirOrderNoteCurrencySummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
@Repository public interface TekirOrderNoteCurrencySummaryRepository extends JpaRepository<TekirOrderNoteCurrencySummary, Long>, JpaSpecificationExecutor<TekirOrderNoteCurrencySummary> {}
