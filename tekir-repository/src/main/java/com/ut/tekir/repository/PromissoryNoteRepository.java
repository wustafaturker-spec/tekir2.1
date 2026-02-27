package com.ut.tekir.repository;

import com.ut.tekir.common.entity.PromissoryNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PromissoryNoteRepository extends JpaRepository<PromissoryNote, Long>, JpaSpecificationExecutor<PromissoryNote> {
}
