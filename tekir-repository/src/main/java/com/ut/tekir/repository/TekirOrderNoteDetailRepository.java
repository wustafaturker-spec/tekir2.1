package com.ut.tekir.repository;
import com.ut.tekir.common.entity.TekirOrderNoteDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
@Repository public interface TekirOrderNoteDetailRepository extends JpaRepository<TekirOrderNoteDetail, Long>, JpaSpecificationExecutor<TekirOrderNoteDetail> {}
