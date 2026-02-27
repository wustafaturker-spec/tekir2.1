package com.ut.tekir.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ut.tekir.common.entity.ContactOpportunity;

@Repository
public interface ContactOpportunityRepository extends JpaRepository<ContactOpportunity, Long> {
    List<ContactOpportunity> findByContactIdOrderByStageAscExpectedCloseDateAsc(Long contactId);
}
