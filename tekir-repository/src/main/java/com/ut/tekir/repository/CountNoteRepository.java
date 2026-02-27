package com.ut.tekir.repository;

import com.ut.tekir.common.entity.CountNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountNoteRepository extends JpaRepository<CountNote, Long> {
}
