package com.ut.tekir.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ut.tekir.common.dto.MoneySetDTO;
import com.ut.tekir.common.dto.payment.PaymentDetailDTO;
import com.ut.tekir.common.dto.payment.PaymentFilterModel;
import com.ut.tekir.common.dto.payment.PaymentItemSaveRequest;
import com.ut.tekir.common.dto.payment.PaymentListDTO;
import com.ut.tekir.common.dto.payment.PaymentSaveRequest;
import com.ut.tekir.common.entity.Account;
import com.ut.tekir.common.entity.Contact;
import com.ut.tekir.common.entity.Payment;
import com.ut.tekir.common.entity.PaymentItem;
import com.ut.tekir.common.enums.DocumentType;
import com.ut.tekir.common.enums.FinanceAction;
import com.ut.tekir.common.entity.FinanceTxn;
import com.ut.tekir.common.exception.EntityNotFoundException;
import com.ut.tekir.repository.AccountRepository;
import com.ut.tekir.repository.ContactRepository;
import com.ut.tekir.repository.PaymentRepository;
import com.ut.tekir.repository.FinanceTxnRepository;
import com.ut.tekir.service.mapper.PaymentMapper;
import com.ut.tekir.service.spec.PaymentSpecifications;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ContactRepository contactRepository;
    private final AccountRepository accountRepository;
    private final PaymentMapper paymentMapper;
    private final SequenceService sequenceService;
    private final FinanceTxnRepository financeTxnRepository;

    @Transactional(readOnly = true)
    public Page<PaymentListDTO> search(PaymentFilterModel filter, Pageable pageable) {
        return paymentRepository
                .findAll(PaymentSpecifications.withFilter(filter), pageable)
                .map(paymentMapper::toListDTO);
    }

    @Transactional(readOnly = true)
    public PaymentDetailDTO getById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment", id));
        return toDetailDTOWithTotals(payment);
    }

    public PaymentDetailDTO save(PaymentSaveRequest request) {
        Payment payment;

        if (request.id() != null) {
            payment = paymentRepository.findById(request.id())
                    .orElseThrow(() -> new EntityNotFoundException("Payment", request.id()));
            payment.getItems().clear();
        } else {
            payment = new Payment();
            String prefix = StringUtils.hasText(request.paymentType())
                    ? request.paymentType()
                    : DocumentType.Payment.name();
            payment.setSerial(sequenceService.getNewNumber(prefix + ".serial", 6));
            payment.setCode(sequenceService.getNewNumber(prefix, 6));
            payment.setDate(request.date() != null ? request.date() : LocalDate.now());
        }

        // Map basic fields
        if (StringUtils.hasText(request.paymentType())) {
            payment.setAction(FinanceAction.valueOf(request.paymentType()));
        }
        if (request.date() != null) {
            payment.setDate(request.date());
        }
        payment.setInfo(request.info());

        // Set contact
        Contact contact = contactRepository.findById(request.contactId())
                .orElseThrow(() -> new EntityNotFoundException("Contact", request.contactId()));
        payment.setContact(contact);

        // Set account
        if (request.accountId() != null) {
            Account account = accountRepository.findById(request.accountId())
                    .orElseThrow(() -> new EntityNotFoundException("Account", request.accountId()));
            payment.setAccount(account);
        } else {
            payment.setAccount(null);
        }

        // Map items
        if (request.items() != null) {
            for (PaymentItemSaveRequest itemReq : request.items()) {
                PaymentItem item = new PaymentItem();
                item.setPayment(payment);

                String ccy = StringUtils.hasText(itemReq.currency()) ? itemReq.currency() : "TRY";
                BigDecimal amount = itemReq.amount() != null ? itemReq.amount() : BigDecimal.ZERO;
                item.getAmount().setValue(amount);
                item.getAmount().setCurrency(ccy);
                item.getAmount().setLocalAmount(amount);

                item.setInfo(itemReq.info());
                item.setLineCode(itemReq.financeAction());

                payment.getItems().add(item);
            }
        }

        payment = paymentRepository.save(payment);

        // Create finance transactions (FINANCE_TXN) for the contact
        createFinanceTransactions(payment);

        return toDetailDTOWithTotals(payment);
    }

    public void delete(Long id) {
        if (!paymentRepository.existsById(id)) {
            throw new EntityNotFoundException("Payment", id);
        }
        financeTxnRepository.deleteByDocumentIdAndDocumentType(id, DocumentType.Payment);
        financeTxnRepository.deleteByDocumentIdAndDocumentType(id, DocumentType.Collection);
        paymentRepository.deleteById(id);
    }

    private PaymentDetailDTO toDetailDTOWithTotals(Payment entity) {
        PaymentDetailDTO base = paymentMapper.toDetailDTO(entity);
        BigDecimal total = paymentMapper.calculateTotal(entity);
        String ccy = paymentMapper.getFirstCurrency(entity);
        return new PaymentDetailDTO(
                base.id(), base.code(), base.date(), base.paymentType(),
                base.contactId(), base.contactCode(), base.contactFullname(),
                base.accountId(), base.accountName(),
                new MoneySetDTO(ccy, total, total),
                base.items(), base.info(),
                base.createDate(), base.createUser(), base.updateDate());
    }

    public void rebuildFinanceTransactions() {
        List<Payment> payments = paymentRepository.findAll();
        for (Payment payment : payments) {
            createFinanceTransactions(payment);
        }
    }

    private void createFinanceTransactions(Payment payment) {
        financeTxnRepository.deleteByDocumentIdAndDocumentType(payment.getId(), DocumentType.Payment);
        financeTxnRepository.deleteByDocumentIdAndDocumentType(payment.getId(), DocumentType.Collection);

        if (payment.getAction() == null || payment.getContact() == null)
            return;

        BigDecimal total = paymentMapper.calculateTotal(payment);
        if (total == null || total.compareTo(BigDecimal.ZERO) == 0)
            return;

        // Collection = Customer paid us, reducing his debt (Credit/Alacak)
        // Payment = We paid vendor, reducing our debt (Debit/Borç)
        FinanceAction action = payment.getAction() == FinanceAction.Credit
                ? FinanceAction.Credit
                : FinanceAction.Debit;

        DocumentType docType = payment.getAction() == FinanceAction.Credit
                ? DocumentType.Collection
                : DocumentType.Payment;

        FinanceTxn txn = new FinanceTxn();
        txn.setContact(payment.getContact());
        txn.setAction(action);
        txn.setDocumentType(docType);
        txn.setDocumentId(payment.getId());
        txn.setCode(payment.getCode());
        txn.setDate(payment.getDate() != null ? payment.getDate() : LocalDate.now());
        txn.setInfo(payment.getInfo());

        String currency = paymentMapper.getFirstCurrency(payment);
        txn.getAmount().setValue(total);
        txn.getAmount().setCurrency(currency);
        txn.getAmount().setLocalAmount(total);

        financeTxnRepository.save(txn);
    }
}
