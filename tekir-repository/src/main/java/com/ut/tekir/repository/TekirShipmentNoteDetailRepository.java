package com.ut.tekir.repository;
import com.ut.tekir.common.entity.TekirShipmentNoteDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
@Repository public interface TekirShipmentNoteDetailRepository extends JpaRepository<TekirShipmentNoteDetail, Long>, JpaSpecificationExecutor<TekirShipmentNoteDetail> {}
