package com.ut.tekir.tenant.service;

import com.ut.tekir.tenant.dto.TenantDetailDTO;
import com.ut.tekir.tenant.dto.TenantListDTO;
import com.ut.tekir.tenant.entity.Tenant;
import com.ut.tekir.tenant.entity.TenantStatus;
import com.ut.tekir.tenant.exception.TenantNotFoundException;
import com.ut.tekir.tenant.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TenantService {

    private final TenantRepository tenantRepository;

    @Transactional(readOnly = true)
    public Tenant findById(Long id) {
        return tenantRepository.findById(id)
                .orElseThrow(() -> new TenantNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public Tenant findBySlug(String slug) {
        return tenantRepository.findBySlug(slug)
                .orElseThrow(() -> new TenantNotFoundException("slug", slug));
    }

    @Transactional(readOnly = true)
    public Long findTenantIdBySlug(String slug) {
        return findBySlug(slug).getId();
    }

    @Transactional(readOnly = true)
    public List<TenantListDTO> findAll() {
        return tenantRepository.findAll().stream()
                .map(this::toListDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TenantListDTO> findByStatus(TenantStatus status) {
        return tenantRepository.findByStatus(status).stream()
                .map(this::toListDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public TenantDetailDTO getDetail(Long id) {
        Tenant tenant = findById(id);
        return toDetailDTO(tenant);
    }

    @Transactional
    public Tenant create(Tenant tenant) {
        if (tenantRepository.existsBySlug(tenant.getSlug())) {
            throw new com.ut.tekir.common.exception.BusinessException(
                    "SLUG_EXISTS", "Bu slug zaten kullaniliyor: " + tenant.getSlug());
        }
        return tenantRepository.save(tenant);
    }

    @Transactional
    public Tenant update(Long id, Tenant updates) {
        Tenant tenant = findById(id);
        tenant.setName(updates.getName());
        tenant.setDomain(updates.getDomain());
        tenant.setLogoUrl(updates.getLogoUrl());
        tenant.setTaxNumber(updates.getTaxNumber());
        tenant.setTaxOffice(updates.getTaxOffice());
        tenant.setSettings(updates.getSettings());
        tenant.setMaxUsers(updates.getMaxUsers());
        return tenantRepository.save(tenant);
    }

    @Transactional
    public void suspend(Long id) {
        Tenant tenant = findById(id);
        tenant.setStatus(TenantStatus.SUSPENDED);
        tenant.setActive(false);
        tenantRepository.save(tenant);
        log.info("Tenant suspended: id={}, slug={}", id, tenant.getSlug());
    }

    @Transactional
    public void activate(Long id) {
        Tenant tenant = findById(id);
        tenant.setStatus(TenantStatus.ACTIVE);
        tenant.setActive(true);
        tenantRepository.save(tenant);
        log.info("Tenant activated: id={}, slug={}", id, tenant.getSlug());
    }

    @Transactional(readOnly = true)
    public long countByStatus(TenantStatus status) {
        return tenantRepository.countByStatus(status);
    }

    @Transactional(readOnly = true)
    public long countAll() {
        return tenantRepository.count();
    }

    private TenantListDTO toListDTO(Tenant t) {
        return new TenantListDTO(
                t.getId(), t.getName(), t.getSlug(),
                t.getStatus(), t.getActive(), t.getMaxUsers(),
                t.getCreateDate());
    }

    private TenantDetailDTO toDetailDTO(Tenant t) {
        return new TenantDetailDTO(
                t.getId(), t.getName(), t.getSlug(), t.getDomain(),
                t.getStatus(), t.getSettings(), t.getLogoUrl(),
                t.getTaxNumber(), t.getTaxOffice(), t.getMaxUsers(),
                t.getActive(), t.getCreateDate(), t.getUpdateDate());
    }
}
