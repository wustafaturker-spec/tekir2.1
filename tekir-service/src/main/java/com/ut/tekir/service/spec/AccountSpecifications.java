package com.ut.tekir.service.spec;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.ut.tekir.common.dto.account.AccountFilterModel;
import com.ut.tekir.common.entity.Account;
import com.ut.tekir.common.enums.AccountType;

import jakarta.persistence.criteria.Predicate;

public class AccountSpecifications {

    public static Specification<Account> maxFilter(AccountFilterModel filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(filter.code())) {
                predicates.add(cb.like(cb.lower(root.get("code")), "%" + filter.code().toLowerCase() + "%"));
            }

            if (StringUtils.hasText(filter.name())) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + filter.name().toLowerCase() + "%"));
            }

            if (StringUtils.hasText(filter.accountType())) {
                try {
                    AccountType type = AccountType.valueOf(filter.accountType());
                    predicates.add(cb.equal(root.get("accountType"), type));
                } catch (IllegalArgumentException e) {
                    // Ignore invalid enum values or handle as needed
                }
            }

            if (StringUtils.hasText(filter.currency())) {
                predicates.add(cb.equal(root.get("currency"), filter.currency()));
            }

            if (filter.organizationId() != null) {
                 predicates.add(cb.equal(root.get("organization").get("id"), filter.organizationId()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
