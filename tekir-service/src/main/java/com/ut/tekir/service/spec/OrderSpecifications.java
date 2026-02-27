package com.ut.tekir.service.spec;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.ut.tekir.common.dto.order.OrderFilterModel;
import com.ut.tekir.common.entity.OrderNote;
import com.ut.tekir.common.enums.TradeAction;

import jakarta.persistence.criteria.Predicate;

public final class OrderSpecifications {

    private OrderSpecifications() {
    }

    public static Specification<OrderNote> withFilter(OrderFilterModel filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(filter.getCode())) {
                predicates.add(cb.like(cb.lower(root.get("code")),
                        filter.getCode().toLowerCase() + "%"));
            }
            if (StringUtils.hasText(filter.getSerial())) {
                predicates.add(cb.like(cb.lower(root.get("serial")),
                        filter.getSerial().toLowerCase() + "%"));
            }
            if (StringUtils.hasText(filter.getReference())) {
                predicates.add(cb.like(cb.lower(root.get("reference")),
                        "%" + filter.getReference().toLowerCase() + "%"));
            }
            if (StringUtils.hasText(filter.getTradeAction())) {
                predicates.add(cb.equal(root.get("action").as(Integer.class),
                        TradeAction.valueOf(filter.getTradeAction()).ordinal()));
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
            if (filter.getWarehouseId() != null) {
                predicates.add(cb.equal(root.get("warehouse").get("id"), filter.getWarehouseId()));
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
