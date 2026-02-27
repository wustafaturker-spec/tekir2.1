package com.ut.tekir.repository;

import com.ut.tekir.common.entity.DocumentMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentMatchRepository extends JpaRepository<DocumentMatch, Long>, JpaSpecificationExecutor<DocumentMatch> {
}
