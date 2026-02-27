package com.ut.tekir.repository;

import com.ut.tekir.common.entity.Sequence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SequenceRepository extends JpaRepository<Sequence, Long>, JpaSpecificationExecutor<Sequence> {
}
