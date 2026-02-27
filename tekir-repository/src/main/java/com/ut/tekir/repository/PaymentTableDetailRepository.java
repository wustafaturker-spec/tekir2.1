package com.ut.tekir.repository;

import com.ut.tekir.common.entity.PaymentTableDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentTableDetailRepository extends JpaRepository<PaymentTableDetail, Long>, JpaSpecificationExecutor<PaymentTableDetail> {
}
