package com.ut.tekir.repository;

import com.ut.tekir.common.entity.PaymentItemPromissoryNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentItemPromissoryNoteRepository extends JpaRepository<PaymentItemPromissoryNote, Long>, JpaSpecificationExecutor<PaymentItemPromissoryNote> {
}
