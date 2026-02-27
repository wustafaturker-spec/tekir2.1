package com.ut.tekir.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ut.tekir.common.entity.Foundation;
import com.ut.tekir.common.exception.EntityNotFoundException;
import com.ut.tekir.repository.FoundationRepository;

import lombok.RequiredArgsConstructor;

/**
 * Foundation service — replaces legacy FoundationHomeBean.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class FoundationService {

    private final FoundationRepository foundationRepository;

    @Transactional(readOnly = true)
    public List<Foundation> findAll() {
        return foundationRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Foundation getById(Long id) {
        return foundationRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Foundation", id));
    }

    public Foundation save(Foundation foundation) {
        return foundationRepository.save(foundation);
    }

    public void delete(Long id) {
        if (!foundationRepository.existsById(id)) {
            throw new EntityNotFoundException("Foundation", id);
        }
        foundationRepository.deleteById(id);
    }
}
