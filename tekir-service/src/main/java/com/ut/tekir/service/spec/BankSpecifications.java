package com.ut.tekir.service.spec;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.ut.tekir.common.dto.bank.BankFilterModel;
import com.ut.tekir.common.entity.Bank;

import jakarta.persistence.criteria.Predicate;

public class BankSpecifications {

    public static Specification<Bank> maxFilter(BankFilterModel filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.active() != null) {
                predicates.add(cb.equal(root.get("active"), filter.active()));
            }

            if (StringUtils.hasText(filter.code())) {
                predicates.add(cb.like(cb.lower(root.get("code")), "%" + filter.code().toLowerCase() + "%"));
            }

            if (StringUtils.hasText(filter.name())) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + filter.name().toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
