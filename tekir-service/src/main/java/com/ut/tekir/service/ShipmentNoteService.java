package com.ut.tekir.service;

import com.ut.tekir.common.embeddable.Quantity;
import com.ut.tekir.common.entity.ProductTxn;
import com.ut.tekir.common.entity.ShipmentItem;
import com.ut.tekir.common.entity.ShipmentNote;
import com.ut.tekir.common.entity.TekirShipmentNote;
import com.ut.tekir.common.enums.DocumentType;
import com.ut.tekir.common.enums.FinanceAction;
import com.ut.tekir.common.enums.TradeAction;
import com.ut.tekir.repository.ProductTxnRepository;
import com.ut.tekir.repository.ShipmentNoteRepository;
import com.ut.tekir.repository.TekirShipmentNoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class ShipmentNoteService {

    private final ShipmentNoteRepository shipmentNoteRepository;
    private final TekirShipmentNoteRepository tekirShipmentNoteRepository;
    private final ProductTxnRepository productTxnRepository;
    private final SequenceService sequenceService;

    // --- ShipmentNote ---

    public Page<ShipmentNote> findAll(Pageable pageable) {
        return shipmentNoteRepository.findAll(pageable);
    }

    public ShipmentNote findById(Long id) {
        return shipmentNoteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ShipmentNote not found: " + id));
    }

    public ShipmentNote save(ShipmentNote note) {
        if (note.getId() == null) {
            // Use correct document type name for sequence based on trade direction
            String docTypeName = note.getAction() == TradeAction.Sale
                    ? DocumentType.SaleShipmentNote.name()
                    : DocumentType.PurchaseShipmentNote.name();
            note.setCode(sequenceService.getNewNumber(docTypeName, 6));
            note.setDate(LocalDate.now());
        }

        ShipmentNote saved = shipmentNoteRepository.save(note);

        // Create stock transactions
        productTxnRepository.deleteByDocumentId(saved.getId());

        if (saved.getAction() != null && saved.getItems() != null) {
            // Purchase shipment: stock enters (Debit); Sale shipment: stock exits (Credit)
            FinanceAction action = saved.getAction() == TradeAction.Purchase
                    ? FinanceAction.Debit : FinanceAction.Credit;
            DocumentType docType = saved.getAction() == TradeAction.Purchase
                    ? DocumentType.PurchaseShipmentNote : DocumentType.SaleShipmentNote;

            for (ShipmentItem item : saved.getItems()) {
                if (item.getProduct() == null) continue;
                BigDecimal qty = item.getQuantity() != null ? item.getQuantity().getValue() : null;
                if (qty == null || qty.compareTo(BigDecimal.ZERO) == 0) continue;

                String unit = item.getQuantity().getUnit() != null ? item.getQuantity().getUnit() : "";

                ProductTxn txn = new ProductTxn();
                txn.setProduct(item.getProduct());
                txn.setWarehouse(saved.getWarehouse());
                txn.setAction(action);
                txn.setDocumentType(docType);
                txn.setDocumentId(saved.getId());
                txn.setDate(saved.getDate());
                txn.setQuantity(new Quantity(qty, unit));
                txn.setUnitPrice(item.getUnitPrice() != null ? item.getUnitPrice().getValue() : BigDecimal.ZERO);
                txn.setReference(saved.getCode());
                productTxnRepository.save(txn);
            }
        }

        return saved;
    }

    public void delete(Long id) {
        productTxnRepository.deleteByDocumentId(id);
        shipmentNoteRepository.deleteById(id);
    }

    // --- TekirShipmentNote (Extended) ---

    public Page<TekirShipmentNote> findAllTekir(Pageable pageable) {
        return tekirShipmentNoteRepository.findAll(pageable);
    }

    public TekirShipmentNote findTekirById(Long id) {
        return tekirShipmentNoteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("TekirShipmentNote not found: " + id));
    }

    public TekirShipmentNote saveTekir(TekirShipmentNote note) {
        return tekirShipmentNoteRepository.save(note);
    }
}
