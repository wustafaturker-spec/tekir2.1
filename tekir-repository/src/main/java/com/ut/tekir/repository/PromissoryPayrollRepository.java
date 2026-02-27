package com.ut.tekir.repository;

import com.ut.tekir.common.entity.PromissoryPayroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromissoryPayrollRepository extends JpaRepository<PromissoryPayroll, Long> {
}
