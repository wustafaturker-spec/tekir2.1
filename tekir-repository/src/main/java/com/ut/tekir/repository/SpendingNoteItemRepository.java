package com.ut.tekir.repository;

import com.ut.tekir.common.entity.SpendingNoteItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SpendingNoteItemRepository extends JpaRepository<SpendingNoteItem, Long>, JpaSpecificationExecutor<SpendingNoteItem> {
}
