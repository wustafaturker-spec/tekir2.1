package com.ut.tekir.repository;

import com.ut.tekir.common.entity.OrderNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderNoteRepository extends JpaRepository<OrderNote, Long>, JpaSpecificationExecutor<OrderNote> {
    Optional<OrderNote> findByCode(String code);
    boolean existsByCodeAndIdNot(String code, Long id);
}
