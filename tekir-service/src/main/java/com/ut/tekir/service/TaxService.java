package com.ut.tekir.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ut.tekir.common.entity.Tax;
import com.ut.tekir.common.exception.EntityNotFoundException;
import com.ut.tekir.repository.TaxRepository;

import lombok.RequiredArgsConstructor;

/**
 * Tax service — replaces legacy TaxHomeBean.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class TaxService {

    private final TaxRepository taxRepository;

    @Transactional(readOnly = true)
    public List<Tax> findAll() {
        return taxRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Tax> findAllActive() {
        return taxRepository.findAll((root, query, cb) ->
            cb.isTrue(root.get("active"))
        );
    }

    @Transactional(readOnly = true)
    public Tax getById(Long id) {
        return taxRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Tax", id));
    }

    public Tax save(Tax tax) {
        return taxRepository.save(tax);
    }

    public void delete(Long id) {
        if (!taxRepository.existsById(id)) {
            throw new EntityNotFoundException("Tax", id);
        }
        taxRepository.deleteById(id);
    }
}
