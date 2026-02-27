package com.ut.tekir.service.spec;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.ut.tekir.common.dto.contact.ContactFilterModel;
import com.ut.tekir.common.entity.Contact;

import jakarta.persistence.criteria.Predicate;

/**
 * JPA Specification: Contact filtreleme.
 * `name` tek isim alanı üzerinden arama yapar.
 */
public final class ContactSpecifications {

    private ContactSpecifications() {
    }

    public static Specification<Contact> withFilter(ContactFilterModel filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(filter.getCode())) {
                predicates.add(cb.like(cb.lower(root.get("code")),
                        filter.getCode().toLowerCase() + "%"));
            }
            if (StringUtils.hasText(filter.getName())) {
                predicates.add(cb.like(cb.lower(root.get("name")),
                        "%" + filter.getName().toLowerCase() + "%"));
            }
            if (StringUtils.hasText(filter.getTaxNumber())) {
                predicates.add(cb.equal(root.get("taxNumber"), filter.getTaxNumber()));
            }
            if (StringUtils.hasText(filter.getSsn())) {
                predicates.add(cb.equal(root.get("ssn"), filter.getSsn()));
            }
            if (filter.getCategoryId() != null) {
                predicates.add(cb.equal(root.get("category").get("id"), filter.getCategoryId()));
            }
            if (filter.getOrganizationId() != null) {
                predicates.add(cb.equal(root.get("organization").get("id"), filter.getOrganizationId()));
            }
            if (filter.getCustomerType() != null && filter.getCustomerType()) {
                predicates.add(cb.isTrue(root.get("customerType")));
            }
            if (filter.getProviderType() != null && filter.getProviderType()) {
                predicates.add(cb.isTrue(root.get("providerType")));
            }
            if (filter.getPersonnelType() != null && filter.getPersonnelType()) {
                predicates.add(cb.isTrue(root.get("personnelType")));
            }
            if (filter.getActive() != null) {
                predicates.add(cb.equal(root.get("active"), filter.getActive()));
            }
            if (filter.getEntityType() != null) {
                predicates.add(cb.equal(root.get("entityType"), filter.getEntityType()));
            }
            if (filter.getAccountStatus() != null) {
                predicates.add(cb.equal(root.get("accountStatus"), filter.getAccountStatus()));
            }
            if (filter.getEInvoiceRegistered() != null) {
                predicates.add(cb.equal(root.get("eInvoiceRegistered"), filter.getEInvoiceRegistered()));
            }
            if (StringUtils.hasText(filter.getSector())) {
                predicates.add(cb.like(cb.lower(root.get("sector")),
                        "%" + filter.getSector().toLowerCase() + "%"));
            }
            if (StringUtils.hasText(filter.getCustomerSegment())) {
                predicates.add(cb.equal(root.get("customerSegment"), filter.getCustomerSegment()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
