package com.ut.tekir.repository;

import com.ut.tekir.common.entity.ExpenseNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseNoteRepository extends JpaRepository<ExpenseNote, Long> {
}
