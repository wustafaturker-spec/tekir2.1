package com.ut.tekir.service;

import com.ut.tekir.common.entity.TekirOrderNote;
import com.ut.tekir.repository.TekirOrderNoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class TekirOrderNoteService {

    private final TekirOrderNoteRepository tekirOrderNoteRepository;

    public Page<TekirOrderNote> findAll(Pageable pageable) {
        return tekirOrderNoteRepository.findAll(pageable);
    }

    public TekirOrderNote findById(Long id) {
        return tekirOrderNoteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("TekirOrderNote not found: " + id));
    }

    public TekirOrderNote save(TekirOrderNote orderNote) {
        return tekirOrderNoteRepository.save(orderNote);
    }

    public void delete(Long id) {
        tekirOrderNoteRepository.deleteById(id);
    }
}
