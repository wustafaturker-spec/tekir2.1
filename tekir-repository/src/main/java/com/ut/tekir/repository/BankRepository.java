package com.ut.tekir.repository;

import com.ut.tekir.common.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long>, JpaSpecificationExecutor<Bank> {
    Optional<Bank> findByCode(String code);
    boolean existsByCodeAndIdNot(String code, Long id);
}
