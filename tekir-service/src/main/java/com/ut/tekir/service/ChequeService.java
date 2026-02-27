package com.ut.tekir.service;

import com.ut.tekir.common.dto.cheque.ChequeDetailDTO;
import com.ut.tekir.common.dto.cheque.ChequeFilterModel;
import com.ut.tekir.common.dto.cheque.ChequeListDTO;
import com.ut.tekir.common.dto.cheque.ChequeSaveRequest;
import com.ut.tekir.common.dto.cheque.ChequePayrollDetailDTO;
import com.ut.tekir.common.dto.cheque.ChequePayrollSaveRequest;
import com.ut.tekir.common.entity.*;
import com.ut.tekir.common.entity.Contact;
import com.ut.tekir.common.enums.ChequeStatus;
import com.ut.tekir.common.enums.DocumentType;
import com.ut.tekir.repository.*;
import com.ut.tekir.service.mapper.ChequeMapper;
import com.ut.tekir.service.spec.ChequeSpecifications;
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
public class ChequeService {

    private final ChequeRepository chequeRepository;
    private final ChequeHistoryRepository chequeHistoryRepository;
    private final ChequePayrollRepository chequePayrollRepository;
    private final ChequeStubRepository chequeStubRepository;
    private final ContactRepository contactRepository;
    private final AccountRepository accountRepository;
    private final SequenceService sequenceService;
    private final ChequeMapper chequeMapper;

    // --- Cheque CRUD ---

    public Page<ChequeListDTO> search(ChequeFilterModel filter, Pageable pageable) {
        return chequeRepository.findAll(ChequeSpecifications.maxFilter(filter), pageable)
                .map(chequeMapper::toListDTO);
    }

    public ChequeDetailDTO get(Long id) {
        Cheque cheque = chequeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cheque not found: " + id));
        return chequeMapper.toDetailDTO(cheque);
    }

    public ChequeDetailDTO create(ChequeSaveRequest request) {
        Cheque cheque = new Cheque();
        cheque.setCode(sequenceService.getNewNumber("Cheque", 6)); // Auto-generate code
        // Cheque usually has Reference Number manually entered or auto.
        // Let's assume auto for now if not provided, or just use what we have.
        // SaveRequest has: id, bankName, bankBranch, accountNo, iban, chequeOwner,
        // amount, currency, maturityDate, paymentPlace, info.
        // No code in request. So we generate it.
        mapRequestToEntity(request, cheque);

        cheque = chequeRepository.save(cheque);
        return get(cheque.getId());
    }

    public ChequeDetailDTO update(Long id, ChequeSaveRequest request) {
        Cheque cheque = chequeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cheque not found: " + id));
        mapRequestToEntity(request, cheque);

        cheque = chequeRepository.save(cheque);
        return get(cheque.getId());
    }

    public void delete(Long id) {
        chequeRepository.deleteById(id);
    }

    // --- Status Changes ---

    public ChequeDetailDTO changeStatus(Long id, ChequeStatus newStatus) {
        Cheque cheque = chequeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cheque not found: " + id));

        createHistory(cheque, newStatus, null, "Status changed manually to " + newStatus);

        cheque.setStatus(newStatus);
        cheque = chequeRepository.save(cheque);
        return chequeMapper.toDetailDTO(cheque);
    }

    public ChequeDetailDTO endorse(Long id, Long targetContactId) {
        Cheque cheque = chequeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cheque not found: " + id));

        // Allow endorsement only if in Portfolio (or maybe specific states like
        // Returned?)
        if (cheque.getStatus() != ChequeStatus.Portfoy) {
            throw new IllegalStateException("Only cheques in Portfolio (Portfoy) can be endorsed.");
        }

        Contact targetContact = contactRepository.findById(targetContactId)
                .orElseThrow(() -> new EntityNotFoundException("Contact not found: " + targetContactId));

        // Update Cheque
        cheque.setStatus(ChequeStatus.Ciro);

        createHistory(cheque, ChequeStatus.Ciro, targetContact, "Endorsed to " + targetContact.getName());

        cheque = chequeRepository.save(cheque);
        return chequeMapper.toDetailDTO(cheque);
    }

    private void createHistory(Cheque cheque, ChequeStatus status, Contact contact, String info) {
        ChequeHistory history = new ChequeHistory();
        history.setOwner(cheque);
        history.setStatus(status);
        history.setContact(contact);
        history.setInfo(info);
        history.setProcessDate(LocalDate.now());
        history.setSource("User");
        chequeHistoryRepository.save(history);
    }

    public List<ChequeListDTO> findByStatus(ChequeStatus status) {
        // This likely needs a better implementation for lists, or use search with
        // filter
        return chequeRepository.findAll().stream()
                .filter(c -> c.getStatus() == status)
                .map(chequeMapper::toListDTO)
                .toList();
    }

    // --- Payroll ---

    public ChequePayrollDetailDTO savePayroll(ChequePayrollSaveRequest request) {
        ChequePayroll payroll;
        if (request.id() != null) {
            payroll = chequePayrollRepository.findById(request.id())
                    .orElseThrow(() -> new EntityNotFoundException("Payroll not found: " + request.id()));
            payroll.getItems().clear();
        } else {
            payroll = new ChequePayroll();
            payroll.setCode(sequenceService.getNewNumber(DocumentType.ChequePaymentPayroll.name(), 6));
            payroll.setDate(request.date() != null ? request.date() : LocalDate.now());
        }

        payroll.setSerial(request.serial());
        payroll.setReference(request.reference());
        payroll.setInfo(request.info());
        if (request.date() != null) {
            payroll.setDate(request.date());
        }
        payroll.setPayrollType(request.payrollType());

        if (request.contactId() != null) {
            Contact contact = contactRepository.findById(request.contactId())
                    .orElseThrow(() -> new EntityNotFoundException("Contact not found: " + request.contactId()));
            payroll.setContact(contact);
        } else {
            payroll.setContact(null);
        }

        if (request.accountId() != null) {
            Account account = accountRepository.findById(request.accountId())
                    .orElseThrow(() -> new EntityNotFoundException("Account not found: " + request.accountId()));
            payroll.setAccount(account);
        } else {
            payroll.setAccount(null);
        }

        if (request.details() != null) {
            for (com.ut.tekir.common.dto.cheque.ChequePayrollDetailSaveRequest detailReq : request.details()) {
                Cheque cheque = chequeRepository.findById(detailReq.chequeId())
                        .orElseThrow(() -> new EntityNotFoundException("Cheque not found: " + detailReq.chequeId()));

                ChequePayrollDetail detail = new ChequePayrollDetail();
                detail.setOwner(payroll);
                detail.setCheque(cheque);
                detail.setInfo(detailReq.info());
                payroll.getItems().add(detail);

                ChequeStatus newStatus = determineStatusFromPayrollType(request.payrollType());
                if (newStatus != null && cheque.getStatus() != newStatus) {
                    createHistory(cheque, newStatus, payroll.getContact(),
                            "Bordro işlemi (" + request.payrollType().name() + "): " + payroll.getCode());
                    cheque.setStatus(newStatus);
                    chequeRepository.save(cheque);
                }
            }
        }

        payroll = chequePayrollRepository.save(payroll);
        return getPayroll(payroll.getId());
    }

    private ChequeStatus determineStatusFromPayrollType(com.ut.tekir.common.enums.PayrollType type) {
        if (type == null)
            return null;
        return switch (type) {
            case ClientEntry -> ChequeStatus.Portfoy;
            case VendorPayment -> ChequeStatus.Ciro;
            case BankCollection -> ChequeStatus.BankaTahsilatta;
            case BankCollateral -> ChequeStatus.BankaTeminat;
            case BankEntry -> ChequeStatus.Portfoy;
            case Collection -> ChequeStatus.KasaTahsilat;
            case Payment -> ChequeStatus.KasaOdeme;
            case StatusChange -> null;
        };
    }

    public ChequePayrollDetailDTO getPayroll(Long id) {
        ChequePayroll payroll = chequePayrollRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payroll not found: " + id));

        java.util.List<ChequeListDTO> chequeDtos = new java.util.ArrayList<>();
        if (payroll.getItems() != null) {
            for (ChequePayrollDetail detail : payroll.getItems()) {
                chequeDtos.add(chequeMapper.toListDTO(detail.getCheque()));
            }
        }

        return new ChequePayrollDetailDTO(
                payroll.getId(),
                payroll.getCode(),
                payroll.getSerial(),
                payroll.getReference(),
                payroll.getDate(),
                payroll.getInfo(),
                payroll.getPayrollType(),
                payroll.getContact() != null ? payroll.getContact().getId() : null,
                payroll.getContact() != null ? payroll.getContact().getName() : null,
                payroll.getAccount() != null ? payroll.getAccount().getId() : null,
                payroll.getAccount() != null ? payroll.getAccount().getName() : null,
                chequeDtos);
    }

    public Page<ChequePayroll> findAllPayrolls(Pageable pageable) {
        return chequePayrollRepository.findAll(pageable);
    }

    // --- Stubs ---

    public List<ChequeStub> findStubsByChequeId(Long chequeId) {
        return chequeStubRepository.findAll().stream()
                .filter(s -> s.getCheque() != null && s.getCheque().getId().equals(chequeId))
                .toList();
    }

    private void mapRequestToEntity(ChequeSaveRequest request, Cheque cheque) {
        cheque.setBankName(request.bankName());
        cheque.setBankBranch(request.bankBranch());
        cheque.setAccountNo(request.accountNo());
        cheque.setIban(request.iban());
        cheque.setChequeOwner(request.chequeOwner());
        cheque.setMaturityDate(request.maturityDate());
        cheque.setPaymentPlace(request.paymentPlace());
        cheque.setInfo(request.info());

        if (request.direction() != null) {
            cheque.setDirection(com.ut.tekir.common.enums.ChequeDirection.valueOf(request.direction()));
        }

        if (request.contactId() != null) {
            Contact contact = contactRepository.findById(request.contactId())
                    .orElseThrow(() -> new EntityNotFoundException("Contact not found: " + request.contactId()));
            cheque.setContact(contact);
            // Also update chequeOwner if empty and contact selected?
            if (cheque.getChequeOwner() == null || cheque.getChequeOwner().isEmpty()) {
                cheque.setChequeOwner(contact.getName());
            }
        }

        if (request.amount() != null) {
            cheque.getAmount().setValue(request.amount());
        }
        if (request.currency() != null) {
            cheque.getAmount().setCurrency(request.currency());
        }

        cheque.setReference(request.chequeNumber()); // Save Cheque Number to Reference field
    }
}
