package com.ut.tekir.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ut.tekir.common.entity.ContactPersonEntry;

@Repository
public interface ContactPersonEntryRepository extends JpaRepository<ContactPersonEntry, Long> {
    List<ContactPersonEntry> findByContactIdOrderByIsDefaultDescFirstNameAsc(Long contactId);
}
