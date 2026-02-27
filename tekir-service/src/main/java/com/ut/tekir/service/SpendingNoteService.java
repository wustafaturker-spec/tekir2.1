package com.ut.tekir.service;

import com.ut.tekir.common.entity.SpendingNote;
import com.ut.tekir.common.enums.DocumentType;
import com.ut.tekir.repository.SpendingNoteRepository;
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
public class SpendingNoteService {

    private final SpendingNoteRepository spendingNoteRepository;
    private final SequenceService sequenceService;

    public Page<SpendingNote> findAll(Pageable pageable) {
        return spendingNoteRepository.findAll(pageable);
    }

    public SpendingNote findById(Long id) {
        return spendingNoteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SpendingNote not found: " + id));
    }

    public SpendingNote save(SpendingNote note) {
        if (note.getId() == null) {
            note.setCode(sequenceService.getNewNumber(DocumentType.SpendingNote.name(), 6));
            note.setDate(LocalDate.now());
        }
        return spendingNoteRepository.save(note);
    }

    public void delete(Long id) {
        spendingNoteRepository.deleteById(id);
    }
}
