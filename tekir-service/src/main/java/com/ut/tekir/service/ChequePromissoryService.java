package com.ut.tekir.service;

import com.ut.tekir.common.entity.Cheque;
import com.ut.tekir.common.entity.PromissoryNote;
import com.ut.tekir.common.entity.ChequePayroll;
import com.ut.tekir.common.entity.PromissoryPayroll;
import com.ut.tekir.common.enums.DocumentType;
import com.ut.tekir.repository.ChequeRepository;
import com.ut.tekir.repository.PromissoryNoteRepository;
import com.ut.tekir.repository.ChequePayrollRepository;
import com.ut.tekir.repository.PromissoryPayrollRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ChequePromissoryService {

    private final ChequeRepository chequeRepository;
    private final PromissoryNoteRepository promissoryNoteRepository;
    private final ChequePayrollRepository chequePayrollRepository;
    private final PromissoryPayrollRepository promissoryPayrollRepository;
    private final SequenceService sequenceService;

    // --- Cheque ---

    public Cheque saveCheque(Cheque cheque) {
        // Cheques usually have external serials (ReferenceNo) but might need internal ID
        return chequeRepository.save(cheque);
    }
    
    public List<Cheque> getAllCheques() {
        return chequeRepository.findAll();
    }

    // --- Promissory ---

    public PromissoryNote savePromissory(PromissoryNote note) {
        return promissoryNoteRepository.save(note);
    }

    // --- Payrolls ---
    
    public ChequePayroll saveChequePayroll(ChequePayroll payroll) {
        if (payroll.getId() == null) {
            payroll.setCode(sequenceService.getNewNumber(DocumentType.ChequePaymentPayroll.name(), 6));
        }
        return chequePayrollRepository.save(payroll);
    }

    public PromissoryPayroll savePromissoryPayroll(PromissoryPayroll payroll) {
        if (payroll.getId() == null) {
            payroll.setCode(sequenceService.getNewNumber(DocumentType.PromissoryPaymentPayroll.name(), 6));
        }
        return promissoryPayrollRepository.save(payroll);
    }
}
