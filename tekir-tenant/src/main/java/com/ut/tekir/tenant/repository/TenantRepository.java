package com.ut.tekir.tenant.repository;

import com.ut.tekir.tenant.entity.Tenant;
import com.ut.tekir.tenant.entity.TenantStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {

    Optional<Tenant> findBySlug(String slug);

    Optional<Tenant> findByDomain(String domain);

    boolean existsBySlug(String slug);

    List<Tenant> findByStatus(TenantStatus status);

    List<Tenant> findByActiveTrue();

    long countByStatus(TenantStatus status);
}
