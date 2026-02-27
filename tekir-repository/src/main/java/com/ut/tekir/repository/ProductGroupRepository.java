package com.ut.tekir.repository;

import com.ut.tekir.common.entity.ProductGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductGroupRepository extends JpaRepository<ProductGroup, Long>, JpaSpecificationExecutor<ProductGroup> {
}
