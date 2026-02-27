package com.ut.tekir.repository;
import com.ut.tekir.common.entity.TekirOrderNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
@Repository public interface TekirOrderNoteRepository extends JpaRepository<TekirOrderNote, Long>, JpaSpecificationExecutor<TekirOrderNote> {}
