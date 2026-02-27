package com.ut.tekir.service;

import com.ut.tekir.common.entity.DocumentMatch;
import com.ut.tekir.repository.DocumentMatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class DocumentMatchService {

    private final DocumentMatchRepository documentMatchRepository;

    public Page<DocumentMatch> findAll(Pageable pageable) {
        return documentMatchRepository.findAll(pageable);
    }

    public DocumentMatch findById(Long id) {
        return documentMatchRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DocumentMatch not found: " + id));
    }

    public DocumentMatch save(DocumentMatch match) {
        return documentMatchRepository.save(match);
    }

    public void delete(Long id) {
        documentMatchRepository.deleteById(id);
    }
}
