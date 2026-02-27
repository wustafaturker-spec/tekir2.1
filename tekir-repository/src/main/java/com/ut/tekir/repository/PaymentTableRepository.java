package com.ut.tekir.repository;

import com.ut.tekir.common.entity.PaymentTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentTableRepository extends JpaRepository<PaymentTable, Long>, JpaSpecificationExecutor<PaymentTable> {
}
