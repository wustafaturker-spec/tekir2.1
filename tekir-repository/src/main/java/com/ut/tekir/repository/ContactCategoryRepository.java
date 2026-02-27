package com.ut.tekir.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.ut.tekir.common.entity.ContactCategory;

@Repository
public interface ContactCategoryRepository extends JpaRepository<ContactCategory, Long>, JpaSpecificationExecutor<ContactCategory> {
    Optional<ContactCategory> findByCode(String code);
}
