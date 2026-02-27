package com.ut.tekir.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ut.tekir.common.entity.Unit;
import com.ut.tekir.common.exception.EntityNotFoundException;
import com.ut.tekir.repository.UnitRepository;

import lombok.RequiredArgsConstructor;

/**
 * Unit service — measurement units CRUD.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class UnitService {

    private final UnitRepository unitRepository;

    @Transactional(readOnly = true)
    public List<Unit> findAll() {
        return unitRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Unit getById(Long id) {
        return unitRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Unit", id));
    }

    public Unit save(Unit unit) {
        return unitRepository.save(unit);
    }

    public void delete(Long id) {
        if (!unitRepository.existsById(id)) {
            throw new EntityNotFoundException("Unit", id);
        }
        unitRepository.deleteById(id);
    }
}
