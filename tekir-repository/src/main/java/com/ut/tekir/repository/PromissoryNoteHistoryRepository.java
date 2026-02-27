package com.ut.tekir.repository;

import com.ut.tekir.common.entity.PromissoryNoteHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromissoryNoteHistoryRepository extends JpaRepository<PromissoryNoteHistory, Long> {
}
