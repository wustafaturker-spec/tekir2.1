package com.ut.tekir.service;

import com.ut.tekir.common.entity.CountNote;
import com.ut.tekir.common.enums.DocumentType;
import com.ut.tekir.repository.CountNoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class CountNoteService {

    private final CountNoteRepository countNoteRepository;
    private final SequenceService sequenceService;

    public Page<CountNote> findAll(Pageable pageable) {
        return countNoteRepository.findAll(pageable);
    }

    public CountNote findById(Long id) {
        return countNoteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("CountNote not found: " + id));
    }

    public CountNote save(CountNote note) {
        if (note.getId() == null) {
            note.setCode(sequenceService.getNewNumber(DocumentType.CountNote.name(), 6));
            note.setDate(LocalDate.now());
        }
        return countNoteRepository.save(note);
    }

    public void delete(Long id) {
        countNoteRepository.deleteById(id);
    }
}
