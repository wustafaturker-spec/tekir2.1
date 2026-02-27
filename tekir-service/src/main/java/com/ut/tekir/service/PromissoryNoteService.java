package com.ut.tekir.service;

import com.ut.tekir.common.dto.promissory.PromissoryNoteDetailDTO;
import com.ut.tekir.common.dto.promissory.PromissoryNoteFilterModel;
import com.ut.tekir.common.dto.promissory.PromissoryNoteListDTO;
import com.ut.tekir.common.dto.promissory.PromissoryNoteSaveRequest;
import com.ut.tekir.common.entity.PromissoryNote;
import com.ut.tekir.common.entity.PromissoryPayroll;
import com.ut.tekir.common.enums.ChequeStatus;
import com.ut.tekir.common.enums.DocumentType;
import com.ut.tekir.common.entity.Contact;
import com.ut.tekir.common.entity.PromissoryNoteHistory;
import com.ut.tekir.repository.ContactRepository;
import com.ut.tekir.repository.PromissoryNoteRepository;
import com.ut.tekir.repository.PromissoryNoteHistoryRepository;
import com.ut.tekir.repository.PromissoryPayrollRepository;
import com.ut.tekir.service.mapper.PromissoryNoteMapper;
import com.ut.tekir.service.spec.PromissoryNoteSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PromissoryNoteService {

    private final PromissoryNoteRepository promissoryNoteRepository;
    private final PromissoryNoteHistoryRepository promissoryNoteHistoryRepository;
    private final PromissoryPayrollRepository promissoryPayrollRepository;
    private final ContactRepository contactRepository;
    private final SequenceService sequenceService;
    private final PromissoryNoteMapper promissoryNoteMapper;

    public Page<PromissoryNoteListDTO> search(PromissoryNoteFilterModel filter, Pageable pageable) {
        return promissoryNoteRepository.findAll(PromissoryNoteSpecifications.maxFilter(filter), pageable)
                .map(promissoryNoteMapper::toListDTO);
    }

    public PromissoryNoteDetailDTO get(Long id) {
        PromissoryNote note = promissoryNoteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Promissory note not found: " + id));
        return promissoryNoteMapper.toDetailDTO(note);
    }

    public PromissoryNoteDetailDTO create(PromissoryNoteSaveRequest request) {
        PromissoryNote note = new PromissoryNote();
        note.setCode(sequenceService.getNewNumber("PromissoryNote", 6));
        mapRequestToEntity(request, note);
        
        note = promissoryNoteRepository.save(note);
        return get(note.getId());
    }

    public PromissoryNoteDetailDTO update(Long id, PromissoryNoteSaveRequest request) {
        PromissoryNote note = promissoryNoteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Promissory note not found: " + id));
        mapRequestToEntity(request, note);
        
        note = promissoryNoteRepository.save(note);
        return get(note.getId());
    }

    public void delete(Long id) {
        promissoryNoteRepository.deleteById(id);
    }

    public PromissoryNoteDetailDTO changeStatus(Long id, ChequeStatus newStatus) {
        PromissoryNote note = promissoryNoteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Promissory note not found: " + id));
        
        createHistory(note, newStatus, null, "Status changed manually to " + newStatus);

        note.setStatus(newStatus);
        note = promissoryNoteRepository.save(note);
        return promissoryNoteMapper.toDetailDTO(note);
    }
    
    public PromissoryNoteDetailDTO endorse(Long id, Long targetContactId) {
        PromissoryNote note = promissoryNoteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Promissory note not found: " + id));

        // Allow endorsement only if in Portfolio
        if (note.getStatus() != ChequeStatus.Portfoy) {
            throw new IllegalStateException("Only promissory notes in Portfolio (Portfoy) can be endorsed.");
        }

        Contact targetContact = contactRepository.findById(targetContactId)
                .orElseThrow(() -> new EntityNotFoundException("Contact not found: " + targetContactId));

        // Update Note
        note.setStatus(ChequeStatus.Ciro);
        
        createHistory(note, ChequeStatus.Ciro, targetContact, "Endorsed to " + targetContact.getName());

        note = promissoryNoteRepository.save(note);
        return promissoryNoteMapper.toDetailDTO(note);
    }
    
    private void createHistory(PromissoryNote note, ChequeStatus status, Contact contact, String info) {
        PromissoryNoteHistory history = new PromissoryNoteHistory();
        history.setOwner(note);
        history.setStatus(status);
        history.setContact(contact);
        history.setInfo(info);
        history.setProcessDate(LocalDate.now());
        history.setSource("User");
        promissoryNoteHistoryRepository.save(history);
    }

    public List<PromissoryNoteListDTO> findByStatus(ChequeStatus status) {
        return promissoryNoteRepository.findAll().stream()
                .filter(n -> n.getStatus() == status)
                .map(promissoryNoteMapper::toListDTO)
                .toList();
    }

    // --- Payroll ---

    public PromissoryPayroll savePayroll(PromissoryPayroll payroll) {
        if (payroll.getId() == null) {
            payroll.setCode(sequenceService.getNewNumber(DocumentType.PromissoryPaymentPayroll.name(), 6));
            payroll.setDate(LocalDate.now());
        }
        return promissoryPayrollRepository.save(payroll);
    }

    public Page<PromissoryPayroll> findAllPayrolls(Pageable pageable) {
        return promissoryPayrollRepository.findAll(pageable);
    }

    private void mapRequestToEntity(PromissoryNoteSaveRequest request, PromissoryNote note) {
        note.setPromissoryOwner(request.promissoryOwner());
        note.setMaturityDate(request.maturityDate());
        note.setPaymentPlace(request.paymentPlace());
        note.setReference(request.promissoryNumber());
        
        // DocumentBase inherits info, let's try setting it if available or ignore if not critical/supported yet
        // note.setInfo(request.info()); // Assuming base has it

        if (request.direction() != null) {
            note.setDirection(com.ut.tekir.common.enums.ChequeDirection.valueOf(request.direction()));
        }

        if (request.contactId() != null) {
            Contact contact = contactRepository.findById(request.contactId())
                    .orElseThrow(() -> new EntityNotFoundException("Contact not found: " + request.contactId()));
            note.setContact(contact);
            
            if (note.getPromissoryOwner() == null || note.getPromissoryOwner().isEmpty()) {
                note.setPromissoryOwner(contact.getName());
            }
        }

        if (request.amount() != null) {
            note.getAmount().setValue(request.amount());
        }
        if (request.currency() != null) {
            note.getAmount().setCurrency(request.currency());
        }
    }
}

