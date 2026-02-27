package com.ut.tekir.service.spec;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.ut.tekir.common.dto.product.ProductFilterModel;
import com.ut.tekir.common.entity.Product;

import jakarta.persistence.criteria.Predicate;

/**
 * JPA Specification for Product filtering.
 */
public final class ProductSpecifications {

    private ProductSpecifications() {
    }

    public static Specification<Product> withFilter(ProductFilterModel filter) {
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
            if (filter.getProductType() != null) {
                predicates.add(cb.equal(root.get("productType"), filter.getProductType()));
            }
            if (filter.getCategoryId() != null) {
                predicates.add(cb.equal(root.get("category").get("id"), filter.getCategoryId()));
            }
            if (StringUtils.hasText(filter.getBarcode())) {
                Predicate bc1 = cb.equal(root.get("barcode1"), filter.getBarcode());
                Predicate bc2 = cb.equal(root.get("barcode2"), filter.getBarcode());
                Predicate bc3 = cb.equal(root.get("barcode3"), filter.getBarcode());
                predicates.add(cb.or(bc1, bc2, bc3));
            }
            if (filter.getActive() != null) {
                predicates.add(cb.equal(root.get("active"), filter.getActive()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
