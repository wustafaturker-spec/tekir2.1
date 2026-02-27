package com.ut.tekir.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ut.tekir.common.entity.Foundation;

@Repository
public interface FoundationRepository extends JpaRepository<Foundation, Long> {
    Optional<Foundation> findByCode(String code);
}
