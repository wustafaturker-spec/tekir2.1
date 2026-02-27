package com.ut.tekir.repository;

import com.ut.tekir.common.entity.ProductUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductUnitRepository extends JpaRepository<ProductUnit, Long>, JpaSpecificationExecutor<ProductUnit> {
}
