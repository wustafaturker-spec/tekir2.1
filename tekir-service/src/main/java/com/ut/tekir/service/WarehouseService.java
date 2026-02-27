package com.ut.tekir.service;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ut.tekir.common.entity.Warehouse;
import com.ut.tekir.common.exception.EntityNotFoundException;
import com.ut.tekir.repository.WarehouseRepository;

import lombok.RequiredArgsConstructor;

/**
 * Warehouse service — replaces legacy WarehouseHomeBean.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;

    @Transactional(readOnly = true)
    public List<Warehouse> findAll() {
        return warehouseRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<Warehouse> search(String code, String name, Boolean active, Pageable pageable) {
        Specification<Warehouse> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (code != null && !code.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("code")), "%" + code.toLowerCase() + "%"));
            }
            if (name != null && !name.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }
            if (active != null) {
                predicates.add(cb.equal(root.get("active"), active));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return warehouseRepository.findAll(spec, pageable);
    }

    @Transactional(readOnly = true)
    public Warehouse getById(Long id) {
        return warehouseRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Warehouse", id));
    }

    public Warehouse save(Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }

    public void delete(Long id) {
        if (!warehouseRepository.existsById(id)) {
            throw new EntityNotFoundException("Warehouse", id);
        }
        warehouseRepository.deleteById(id);
    }
}
