package com.ut.tekir.accounting.repository;

import com.ut.tekir.accounting.entity.ChartAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChartAccountRepository extends JpaRepository<ChartAccount, Long> {

    List<ChartAccount> findByPlanIdAndParentIsNullOrderByCodeAsc(Long planId);

    List<ChartAccount> findByPlanIdOrderByCodeAsc(Long planId);

    Optional<ChartAccount> findByPlanIdAndCode(Long planId, String code);

    boolean existsByPlanIdAndCode(Long planId, String code);

    long countByPlanId(Long planId);

    void deleteByPlanId(Long planId);

    @Modifying
    @Query("UPDATE ChartAccount ca SET ca.parent = null WHERE ca.plan.id = :planId")
    void clearParentRefs(@Param("planId") Long planId);

    @Query("SELECT ca FROM ChartAccount ca WHERE ca.plan.id = :planId " +
           "AND (LOWER(ca.code) LIKE LOWER(CONCAT('%', :q, '%')) " +
           "OR LOWER(ca.name) LIKE LOWER(CONCAT('%', :q, '%'))) " +
           "AND ca.isDetail = true ORDER BY ca.code ASC")
    Page<ChartAccount> suggestAccounts(@Param("planId") Long planId,
                                       @Param("q") String query,
                                       Pageable pageable);

    @Query("SELECT ca FROM ChartAccount ca WHERE ca.plan.id = :planId " +
           "AND (LOWER(ca.code) LIKE LOWER(CONCAT('%', :q, '%')) " +
           "OR LOWER(ca.name) LIKE LOWER(CONCAT('%', :q, '%'))) " +
           "ORDER BY ca.code ASC")
    Page<ChartAccount> searchAccounts(@Param("planId") Long planId,
                                      @Param("q") String query,
                                      Pageable pageable);
}
