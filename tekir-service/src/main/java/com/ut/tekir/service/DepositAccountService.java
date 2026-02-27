package com.ut.tekir.service;

import com.ut.tekir.common.entity.DepositAccount;
import com.ut.tekir.common.enums.DocumentType;
import com.ut.tekir.repository.DepositAccountRepository;
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
public class DepositAccountService {

    private final DepositAccountRepository depositAccountRepository;
    private final SequenceService sequenceService;

    public Page<DepositAccount> findAll(Pageable pageable) {
        return depositAccountRepository.findAll(pageable);
    }

    public DepositAccount findById(Long id) {
        return depositAccountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DepositAccount not found: " + id));
    }

    public DepositAccount save(DepositAccount depositAccount) {
        if (depositAccount.getId() == null) {
            depositAccount.setCode(sequenceService.getNewNumber(DocumentType.DepositAccount.name(), 6));
            depositAccount.setDate(LocalDate.now());
        }
        return depositAccountRepository.save(depositAccount);
    }

    public void delete(Long id) {
        depositAccountRepository.deleteById(id);
    }
}
