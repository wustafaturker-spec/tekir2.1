package com.ut.tekir.repository;

import com.ut.tekir.common.entity.ProductVirement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductVirementRepository extends JpaRepository<ProductVirement, Long> {
}
