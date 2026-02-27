package com.ut.tekir.repository;

import com.ut.tekir.common.entity.PaymentItemCreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentItemCreditCardRepository extends JpaRepository<PaymentItemCreditCard, Long>, JpaSpecificationExecutor<PaymentItemCreditCard> {
}
