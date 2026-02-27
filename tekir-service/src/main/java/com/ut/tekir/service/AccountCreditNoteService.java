package com.ut.tekir.service;

import com.ut.tekir.common.entity.AccountCreditNote;
import com.ut.tekir.common.enums.DocumentType;
import com.ut.tekir.repository.AccountCreditNoteRepository;
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
public class AccountCreditNoteService {

    private final AccountCreditNoteRepository accountCreditNoteRepository;
    private final SequenceService sequenceService;

    public Page<AccountCreditNote> findAll(Pageable pageable) {
        return accountCreditNoteRepository.findAll(pageable);
    }

    public AccountCreditNote findById(Long id) {
        return accountCreditNoteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("AccountCreditNote not found: " + id));
    }

    public AccountCreditNote save(AccountCreditNote note) {
        if (note.getId() == null) {
            note.setCode(sequenceService.getNewNumber(DocumentType.AccountCreditNote.name(), 6));
            note.setDate(LocalDate.now());
        }
        return accountCreditNoteRepository.save(note);
    }

    public void delete(Long id) {
        accountCreditNoteRepository.deleteById(id);
    }
}
