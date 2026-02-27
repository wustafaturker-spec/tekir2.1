package com.ut.tekir.service;

import com.ut.tekir.common.entity.*;
import com.ut.tekir.common.enums.DocumentType;
import com.ut.tekir.common.enums.FinanceAction;
import java.math.BigDecimal;
import com.ut.tekir.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import com.ut.tekir.common.exception.EntityNotFoundException;

/**
 * Service for Stock Movements (Transfer, Virement, CountNote).
 * Generates ProductTxn records.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class StockMovementService {

    private final ProductTransferRepository transferRepository;
    private final ProductVirementRepository virementRepository;
    private final CountNoteRepository countNoteRepository;
    private final ProductTxnRepository productTxnRepository;
    private final SequenceService sequenceService;

    // --- Product Transfer ---

    public org.springframework.data.domain.Page<ProductTransfer> findAllTransfers(org.springframework.data.domain.Pageable pageable) {
        return transferRepository.findAll(pageable);
    }

    public ProductTransfer getTransferById(Long id) {
        return transferRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("ProductTransfer", id));
    }

    public void deleteTransfer(Long id) {
        if (!transferRepository.existsById(id)) {
            throw new EntityNotFoundException("ProductTransfer", id);
        }
        deleteTxns(id);
        transferRepository.deleteById(id);
    }

    public ProductTransfer saveTransfer(ProductTransfer transfer) {
        if (transfer.getId() == null) {
            String seq = sequenceService.getNewNumber(DocumentType.ProductTransfer.name(), 6);
            transfer.setCode(seq);
            if (transfer.getDate() == null) transfer.setDate(LocalDate.now());
        } else {
            // Clear old txns before recreating
            deleteTxns(transfer.getId());
        }

        ProductTransfer saved = transferRepository.save(transfer);

        // Two txns per item: Credit (OUT) from fromWarehouse, Debit (IN) to toWarehouse
        for (ProductTransferItem item : saved.getItems()) {
            if (item.getProduct() == null) continue;
            BigDecimal qty = item.getQuantity() != null ? item.getQuantity().getValue() : null;
            if (qty == null || qty.compareTo(BigDecimal.ZERO) == 0) continue;

            // Stock exits source warehouse
            createTxn(saved, item.getProduct(), saved.getFromWarehouse(), qty, FinanceAction.Credit, null);
            // Stock enters destination warehouse
            createTxn(saved, item.getProduct(), saved.getToWarehouse(), qty, FinanceAction.Debit, null);
        }

        return saved;
    }

    // --- Product Virement ---

    public org.springframework.data.domain.Page<ProductVirement> findAllVirements(org.springframework.data.domain.Pageable pageable) {
        return virementRepository.findAll(pageable);
    }

    public ProductVirement getVirementById(Long id) {
        return virementRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("ProductVirement", id));
    }

    public void deleteVirement(Long id) {
        if (!virementRepository.existsById(id)) {
            throw new EntityNotFoundException("ProductVirement", id);
        }
        deleteTxns(id);
        virementRepository.deleteById(id);
    }

    public ProductVirement saveVirement(ProductVirement virement) {
        if (virement.getId() == null) {
            String seq = sequenceService.getNewNumber(DocumentType.ProductVirement.name(), 6);
            virement.setCode(seq);
            if (virement.getDate() == null) virement.setDate(LocalDate.now());
        } else {
            deleteTxns(virement.getId());
        }

        ProductVirement saved = virementRepository.save(virement);

        // Two txns per item: fromProduct OUT (Credit), toProduct IN (Debit), same warehouse
        for (ProductVirementItem item : saved.getItems()) {
            BigDecimal qty = item.getQuantity() != null ? item.getQuantity().getValue() : null;
            if (qty == null || qty.compareTo(BigDecimal.ZERO) == 0) continue;

            if (item.getFromProduct() != null) {
                createTxn(saved, item.getFromProduct(), saved.getWarehouse(), qty, FinanceAction.Credit, null);
            }
            if (item.getToProduct() != null) {
                createTxn(saved, item.getToProduct(), saved.getWarehouse(), qty, FinanceAction.Debit, null);
            }
        }

        return saved;
    }

    // --- Count Note ---

    public CountNote saveCountNote(CountNote note) {
        if (note.getId() == null) {
            note.setCode(sequenceService.getNewNumber(DocumentType.CountNote.name(), 6));
            note.setDate(LocalDate.now());
        }

        CountNote saved = countNoteRepository.save(note);

        if (Boolean.TRUE.equals(saved.getApproved())) {
            deleteTxns(saved.getId());

            for (CountNoteItem item : saved.getItems()) {
                int diff = item.getCountQuantity() - item.getExistingQuantity();
                if (diff != 0) {
                    FinanceAction action = diff > 0 ? FinanceAction.Debit : FinanceAction.Credit;
                    createTxn(saved, item.getProduct(), saved.getWarehouse(),
                            BigDecimal.valueOf(Math.abs(diff)), action, "Count Correction");
                }
            }
        }
        return saved;
    }

    // --- Helper ---

    private void createTxn(DocumentBase doc, Product product, Warehouse warehouse,
                            BigDecimal amount, FinanceAction action, String info) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) == 0) return;
        ProductTxn txn = new ProductTxn();
        txn.setProduct(product);
        txn.setWarehouse(warehouse);
        txn.getQuantity().setValue(amount);
        txn.getQuantity().setUnit(product != null && product.getUnit() != null ? product.getUnit() : "");
        txn.setAction(action);
        txn.setDocumentType(doc.getDocumentType());
        txn.setDocumentId(doc.getId());
        txn.setSerial(doc.getCode());
        txn.setReference(doc.getCode());
        txn.setDate(doc.getDate());
        txn.setInfo(info);
        txn.setUnitPrice(BigDecimal.ZERO);
        productTxnRepository.save(txn);
    }

    private void deleteTxns(Long docId) {
        productTxnRepository.deleteByDocumentId(docId);
    }
}
