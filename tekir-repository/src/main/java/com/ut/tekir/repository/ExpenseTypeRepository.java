package com.ut.tekir.repository;

import com.ut.tekir.common.entity.ExpenseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseTypeRepository extends JpaRepository<ExpenseType, Long>, JpaSpecificationExecutor<ExpenseType> {
}
