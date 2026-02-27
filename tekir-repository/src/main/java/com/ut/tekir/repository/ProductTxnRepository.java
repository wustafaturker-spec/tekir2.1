package com.ut.tekir.repository;

import com.ut.tekir.common.entity.ProductTxn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductTxnRepository extends JpaRepository<ProductTxn, Long> {
    List<ProductTxn> findByDocumentId(Long documentId);

    void deleteByDocumentId(Long documentId);

    List<ProductTxn> findByProductIdOrderByDateDesc(Long productId);
}
