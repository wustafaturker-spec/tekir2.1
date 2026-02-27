package com.ut.tekir.service.spec;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.ut.tekir.common.dto.promissory.PromissoryNoteFilterModel;
import com.ut.tekir.common.entity.PromissoryNote;
import com.ut.tekir.common.enums.ChequeStatus;

import jakarta.persistence.criteria.Predicate;

public class PromissoryNoteSpecifications {

    public static Specification<PromissoryNote> maxFilter(PromissoryNoteFilterModel filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(filter.code())) {
                predicates.add(cb.like(cb.lower(root.get("code")), "%" + filter.code().toLowerCase() + "%"));
            }

            if (StringUtils.hasText(filter.promissoryOwner())) {
                predicates.add(cb.like(cb.lower(root.get("promissoryOwner")), "%" + filter.promissoryOwner().toLowerCase() + "%"));
            }

            if (StringUtils.hasText(filter.status())) {
                 try {
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
