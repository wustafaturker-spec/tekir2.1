package com.ut.tekir.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ut.tekir.common.entity.Organization;
import com.ut.tekir.common.exception.EntityNotFoundException;
import com.ut.tekir.repository.OrganizationRepository;

import lombok.RequiredArgsConstructor;

/**
 * Organization service — replaces legacy OrganizationHomeBean.
 * Handles hierarchical organization structure.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    @Transactional(readOnly = true)
    public List<Organization> findAll() {
        return organizationRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Organization> findRoots() {
        return organizationRepository.findAll((root, query, cb) ->
            cb.isNull(root.get("parentOrganization"))
        );
    }

    @Transactional(readOnly = true)
    public Organization getById(Long id) {
        return organizationRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Organization", id));
    }

    public Organization save(Organization organization) {
        // Set hasChild flag on parent if applicable
        if (organization.getParentOrganization() != null) {
            Organization parent = organizationRepository.findById(
                    organization.getParentOrganization().getId())
                .orElseThrow(() -> new EntityNotFoundException("Organization",
                    organization.getParentOrganization().getId()));
            parent.setHasChild(Boolean.TRUE);
            organizationRepository.save(parent);
        }
        return organizationRepository.save(organization);
    }

    public void delete(Long id) {
        Organization org = organizationRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Organization", id));

        if (org.getHasChild() != null && org.getHasChild()) {
            throw new com.ut.tekir.common.exception.BusinessException(
                "has.children", "Alt organizasyonları olan organizasyon silinemez");
        }
        organizationRepository.deleteById(id);
    }
}
