package com.ut.tekir.repository;

import com.ut.tekir.common.entity.ProductTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTransferRepository extends JpaRepository<ProductTransfer, Long> {
}
