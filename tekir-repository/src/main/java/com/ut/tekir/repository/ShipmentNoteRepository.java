package com.ut.tekir.repository;

import com.ut.tekir.common.entity.ShipmentNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentNoteRepository extends JpaRepository<ShipmentNote, Long> {
}
