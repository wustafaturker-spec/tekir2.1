package com.ut.tekir.service;

import com.ut.tekir.common.entity.ExpenseNote;
import com.ut.tekir.common.enums.DocumentType;
import com.ut.tekir.repository.ExpenseNoteRepository;
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
public class ExpenseNoteService {

    private final ExpenseNoteRepository expenseNoteRepository;
    private final SequenceService sequenceService;

    public Page<ExpenseNote> findAll(Pageable pageable) {
        return expenseNoteRepository.findAll(pageable);
    }

    public ExpenseNote findById(Long id) {
        return expenseNoteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ExpenseNote not found: " + id));
    }

    public ExpenseNote save(ExpenseNote note) {
        if (note.getId() == null) {
            note.setCode(sequenceService.getNewNumber(DocumentType.ExpenseNote.name(), 6));
            note.setDate(LocalDate.now());
        }
        return expenseNoteRepository.save(note);
    }

    public void delete(Long id) {
        expenseNoteRepository.deleteById(id);
    }
}
