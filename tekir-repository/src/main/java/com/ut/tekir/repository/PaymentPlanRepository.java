package com.ut.tekir.repository;

import com.ut.tekir.common.entity.PaymentPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentPlanRepository extends JpaRepository<PaymentPlan, Long> {
}
