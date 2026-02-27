package com.ut.tekir.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.ut.tekir.common.entity.Tax;

@Repository
public interface TaxRepository extends JpaRepository<Tax, Long>, JpaSpecificationExecutor<Tax> {
    Optional<Tax> findByCode(String code);
}
