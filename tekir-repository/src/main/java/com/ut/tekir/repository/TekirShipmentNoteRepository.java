package com.ut.tekir.repository;
import com.ut.tekir.common.entity.TekirShipmentNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
@Repository public interface TekirShipmentNoteRepository extends JpaRepository<TekirShipmentNote, Long>, JpaSpecificationExecutor<TekirShipmentNote> {}
