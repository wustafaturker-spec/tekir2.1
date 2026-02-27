package com.ut.tekir.service;

import com.ut.tekir.common.entity.DebitCreditVirement;
import com.ut.tekir.common.enums.DocumentType;
import com.ut.tekir.repository.DebitCreditVirementRepository;
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
public class DebitCreditVirementService {

    private final DebitCreditVirementRepository virementRepository;
    private final SequenceService sequenceService;

    public Page<DebitCreditVirement> findAll(Pageable pageable) {
        return virementRepository.findAll(pageable);
    }

    public DebitCreditVirement findById(Long id) {
        return virementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DebitCreditVirement not found: " + id));
    }

    public DebitCreditVirement save(DebitCreditVirement virement) {
        if (virement.getId() == null) {
            virement.setCode(sequenceService.getNewNumber(DocumentType.DebitCreditVirement.name(), 6));
            virement.setDate(LocalDate.now());
        }
        return virementRepository.save(virement);
    }

    public void delete(Long id) {
        virementRepository.deleteById(id);
    }
}
