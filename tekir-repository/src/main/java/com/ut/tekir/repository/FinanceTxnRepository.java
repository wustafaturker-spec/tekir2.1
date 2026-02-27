package com.ut.tekir.repository;

import com.ut.tekir.common.entity.FinanceTxn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FinanceTxnRepository extends JpaRepository<FinanceTxn, Long> {
    List<FinanceTxn> findByAccountId(Long accountId);

    List<FinanceTxn> findByContactIdOrderByDateDesc(Long contactId);

    void deleteByDocumentIdAndDocumentType(Long documentId, com.ut.tekir.common.enums.DocumentType documentType);

    @org.springframework.data.jpa.repository.Query("SELECT f FROM FinanceTxn f WHERE f.contact.id = :contactId AND (cast(:startDate as date) IS NULL OR f.date >= :startDate) AND (cast(:endDate as date) IS NULL OR f.date <= :endDate) ORDER BY f.date ASC, f.id ASC")
    List<FinanceTxn> findTransactionsByContactAndDateRange(
            @org.springframework.data.repository.query.Param("contactId") Long contactId,
            @org.springframework.data.repository.query.Param("startDate") java.time.LocalDate startDate,
            @org.springframework.data.repository.query.Param("endDate") java.time.LocalDate endDate);

    @org.springframework.data.jpa.repository.Query("SELECT f FROM FinanceTxn f WHERE f.contact.id = :contactId AND f.date < :startDate")
    List<FinanceTxn> findTransactionsBeforeDate(
            @org.springframework.data.repository.query.Param("contactId") Long contactId,
            @org.springframework.data.repository.query.Param("startDate") java.time.LocalDate startDate);
}
