package com.ut.tekir.repository;

import com.ut.tekir.common.entity.PaymentCommission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentCommissionRepository extends JpaRepository<PaymentCommission, Long>, JpaSpecificationExecutor<PaymentCommission> {
}
