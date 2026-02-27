package com.ut.tekir.repository;

import com.ut.tekir.common.entity.Pos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PosRepository extends JpaRepository<Pos, Long> {
}
