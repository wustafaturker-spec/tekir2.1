package com.ut.tekir.service;

import com.ut.tekir.common.entity.DebitCreditNote;
import com.ut.tekir.common.enums.DocumentType;
import com.ut.tekir.repository.DebitCreditNoteRepository;
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
public class DebitCreditNoteService {

    private final DebitCreditNoteRepository noteRepository;
    private final SequenceService sequenceService;

    public Page<DebitCreditNote> findAll(Pageable pageable) {
        return noteRepository.findAll(pageable);
    }

    public DebitCreditNote findById(Long id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DebitCreditNote not found: " + id));
    }

    public DebitCreditNote save(DebitCreditNote note) {
        if (note.getId() == null) {
            note.setCode(sequenceService.getNewNumber(DocumentType.DebitCreditNote.name(), 6));
            note.setDate(LocalDate.now());
        }
        return noteRepository.save(note);
    }

    public void delete(Long id) {
        noteRepository.deleteById(id);
    }
}
