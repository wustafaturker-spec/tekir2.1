package com.ut.tekir.service.spec;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.ut.tekir.common.dto.cheque.ChequeFilterModel;
import com.ut.tekir.common.entity.Cheque;
import com.ut.tekir.common.enums.ChequeStatus;

import jakarta.persistence.criteria.Predicate;

public class ChequeSpecifications {

    public static Specification<Cheque> maxFilter(ChequeFilterModel filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(filter.code())) {
                predicates.add(cb.like(cb.lower(root.get("code")), "%" + filter.code().toLowerCase() + "%"));
            }

            if (StringUtils.hasText(filter.bankName())) {
                predicates.add(cb.like(cb.lower(root.get("bankName")), "%" + filter.bankName().toLowerCase() + "%"));
            }

            if (StringUtils.hasText(filter.chequeOwner())) {
                predicates.add(cb.like(cb.lower(root.get("chequeOwner")), "%" + filter.chequeOwner().toLowerCase() + "%"));
            }

            if (StringUtils.hasText(filter.status())) {
                 try {
                    // Assuming status filter comes as string but maps to enum ordinal or string
                    // Checking Cheque entity, status is @Enumerated(EnumType.ORDINAL)
                    // If filter.status() is the Enum name (e.g. "Portfoy"), we need to resolve it.
                    ChequeStatus status = ChequeStatus.valueOf(filter.status());
                    predicates.add(cb.equal(root.get("status"), status));
                } catch (IllegalArgumentException e) {
                    // Ignore
                }
            }

            if (filter.dueDateFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("maturityDate"), filter.dueDateFrom()));
            }

            if (filter.dueDateTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("maturityDate"), filter.dueDateTo()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
