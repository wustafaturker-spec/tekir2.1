package com.ut.tekir.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.ut.tekir.common.entity.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long>, JpaSpecificationExecutor<Contact> {
    Optional<Contact> findByCode(String code);
    boolean existsByCodeAndIdNot(String code, Long id);
    Optional<Contact> findFirstByTaxNumber(String taxNumber);
}
