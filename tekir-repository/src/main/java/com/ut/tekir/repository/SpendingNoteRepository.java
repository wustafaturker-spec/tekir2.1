package com.ut.tekir.repository;

import com.ut.tekir.common.entity.SpendingNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SpendingNoteRepository extends JpaRepository<SpendingNote, Long>, JpaSpecificationExecutor<SpendingNote> {
}
