package com.ut.tekir.repository;

import com.ut.tekir.common.entity.PaymentItemCheque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentItemChequeRepository extends JpaRepository<PaymentItemCheque, Long>, JpaSpecificationExecutor<PaymentItemCheque> {
}
