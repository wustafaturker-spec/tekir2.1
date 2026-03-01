package com.ut.tekir.accounting.repository;

import com.ut.tekir.accounting.entity.AccountPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountPlanRepository extends JpaRepository<AccountPlan, Long> {

    List<AccountPlan> findAllByIsActiveTrueOrderByCodeAsc();

    Optional<AccountPlan> findByCode(String code);

    Optional<AccountPlan> findByIsDefaultTrue();

    boolean existsByCode(String code);

    @Modifying
    @Query("UPDATE AccountPlan p SET p.isDefault = false WHERE p.isDefault = true")
    void clearAllDefaults();
}
