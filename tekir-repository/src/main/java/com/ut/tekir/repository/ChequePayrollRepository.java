package com.ut.tekir.repository;

import com.ut.tekir.common.entity.ChequePayroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChequePayrollRepository extends JpaRepository<ChequePayroll, Long> {
}
