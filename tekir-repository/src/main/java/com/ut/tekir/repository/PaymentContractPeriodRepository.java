package com.ut.tekir.repository;

import com.ut.tekir.common.entity.PaymentContractPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentContractPeriodRepository extends JpaRepository<PaymentContractPeriod, Long>, JpaSpecificationExecutor<PaymentContractPeriod> {
}
