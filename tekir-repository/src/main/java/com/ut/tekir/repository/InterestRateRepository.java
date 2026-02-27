package com.ut.tekir.repository;
import com.ut.tekir.common.entity.InterestRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
@Repository public interface InterestRateRepository extends JpaRepository<InterestRate, Long>, JpaSpecificationExecutor<InterestRate> {}
