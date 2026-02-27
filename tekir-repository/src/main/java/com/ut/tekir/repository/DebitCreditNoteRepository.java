package com.ut.tekir.repository;

import com.ut.tekir.common.entity.DebitCreditNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DebitCreditNoteRepository extends JpaRepository<DebitCreditNote, Long> {
}
