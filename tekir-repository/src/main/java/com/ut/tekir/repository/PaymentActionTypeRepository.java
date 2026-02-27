package com.ut.tekir.repository;

import com.ut.tekir.common.entity.PaymentActionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentActionTypeRepository extends JpaRepository<PaymentActionType, Long>, JpaSpecificationExecutor<PaymentActionType> {
}
