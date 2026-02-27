package com.ut.tekir.service.spec;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.ut.tekir.common.dto.payment.PaymentFilterModel;
import com.ut.tekir.common.entity.Payment;
import com.ut.tekir.common.enums.FinanceAction;

import jakarta.persistence.criteria.Predicate;

public final class PaymentSpecifications {

    private PaymentSpecifications() {
    }

    public static Specification<Payment> withFilter(PaymentFilterModel filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(filter.getCode())) {
                predicates.add(cb.like(cb.lower(root.get("code")),
                        filter.getCode().toLowerCase() + "%"));
            }
            if (StringUtils.hasText(filter.getAction())) {
                String actionStr = filter.getAction();
                FinanceAction action = null;
                if ("Collection".equalsIgnoreCase(actionStr)) {
                    action = FinanceAction.Credit;
                } else if ("Payment".equalsIgnoreCase(actionStr)) {
                    action = FinanceAction.Debit;
                } else {
                    try {
                        action = FinanceAction.valueOf(actionStr);
                    } catch (IllegalArgumentException e) {
                        // Ignore invalid enum values
                    }
                }

                if (action != null) {
                    predicates.add(cb.equal(root.get("action").as(Integer.class), action.ordinal()));
                }
            }
            if (filter.getContactId() != null) {
                predicates.add(cb.equal(root.get("contact").get("id"), filter.getContactId()));
            }
            if (StringUtils.hasText(filter.getContactCode())) {
                predicates.add(cb.like(cb.lower(root.get("contact").get("code")),
                        filter.getContactCode().toLowerCase() + "%"));
            }
            if (StringUtils.hasText(filter.getContactFullname())) {
                predicates.add(cb.like(cb.lower(root.get("contact").get("fullname")),
                        "%" + filter.getContactFullname().toLowerCase() + "%"));
            }
            if (filter.getAccountId() != null) {
                predicates.add(cb.equal(root.get("account").get("id"), filter.getAccountId()));
            }
            if (filter.getDateFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("date"), filter.getDateFrom()));
            }
            if (filter.getDateTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("date"), filter.getDateTo()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
