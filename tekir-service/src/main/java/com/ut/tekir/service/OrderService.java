package com.ut.tekir.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ut.tekir.common.dto.invoice.InvoiceDetailDTO;
import com.ut.tekir.common.dto.invoice.InvoiceItemSaveRequest;
import com.ut.tekir.common.dto.invoice.InvoiceSaveRequest;
import com.ut.tekir.common.dto.order.OrderDetailDTO;
import com.ut.tekir.common.dto.order.OrderFilterModel;
import com.ut.tekir.common.dto.order.OrderItemSaveRequest;
import com.ut.tekir.common.dto.order.OrderListDTO;
import com.ut.tekir.common.dto.order.OrderSaveRequest;
import com.ut.tekir.common.entity.Contact;
import com.ut.tekir.common.entity.OrderItem;
import com.ut.tekir.common.entity.OrderNote;
import com.ut.tekir.common.entity.Product;
import com.ut.tekir.common.entity.Warehouse;
import com.ut.tekir.common.enums.TradeAction;
import com.ut.tekir.common.exception.EntityNotFoundException;
import com.ut.tekir.repository.ContactRepository;
import com.ut.tekir.repository.OrderNoteRepository;
import com.ut.tekir.repository.ProductRepository;
import com.ut.tekir.repository.WarehouseRepository;
import com.ut.tekir.service.mapper.OrderMapper;
import com.ut.tekir.service.spec.OrderSpecifications;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderNoteRepository orderNoteRepository;
    private final ContactRepository contactRepository;
    private final WarehouseRepository warehouseRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;
    private final SequenceService sequenceService;
    private final InvoiceService invoiceService;

    @Transactional(readOnly = true)
    public Page<OrderListDTO> search(OrderFilterModel filter, Pageable pageable) {
        return orderNoteRepository
                .findAll(OrderSpecifications.withFilter(filter), pageable)
                .map(this::toListDTOWithTotals);
    }

    @Transactional(readOnly = true)
    public OrderDetailDTO getById(Long id) {
        OrderNote order = orderNoteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("OrderNote", id));
        return toDetailDTOWithTotals(order);
    }

    public OrderDetailDTO save(OrderSaveRequest request) {
        OrderNote order;

        if (request.id() != null) {
            order = orderNoteRepository.findById(request.id())
                    .orElseThrow(() -> new EntityNotFoundException("OrderNote", request.id()));
            order.getItems().clear();
        } else {
            order = new OrderNote();
            String actionPrefix = StringUtils.hasText(request.tradeAction())
                    ? request.tradeAction()
                    : "Order";
            order.setSerial(sequenceService.getNewNumber(actionPrefix + ".serial", 6));
            order.setCode(sequenceService.getNewNumber(actionPrefix, 6));
            order.setDate(request.date() != null ? request.date() : LocalDate.now());
        }

        // Map basic fields
        if (StringUtils.hasText(request.tradeAction())) {
            order.setAction(TradeAction.valueOf(request.tradeAction()));
        }
        if (StringUtils.hasText(request.serial())) {
            order.setSerial(request.serial());
        }
        if (StringUtils.hasText(request.reference())) {
            order.setReference(request.reference());
        }
        if (request.date() != null) {
            order.setDate(request.date());
        }
        order.setInfo(request.info());

        // Set contact
        Contact contact = contactRepository.findById(request.contactId())
                .orElseThrow(() -> new EntityNotFoundException("Contact", request.contactId()));
        order.setContact(contact);

        // Set warehouse
        if (request.warehouseId() != null) {
            Warehouse warehouse = warehouseRepository.findById(request.warehouseId())
                    .orElseThrow(() -> new EntityNotFoundException("Warehouse", request.warehouseId()));
            order.setWarehouse(warehouse);
        } else {
            order.setWarehouse(null);
        }

        // Map items
        if (request.items() != null) {
            for (OrderItemSaveRequest itemReq : request.items()) {
                OrderItem item = new OrderItem();
                item.setOwner(order);

                Product product = productRepository.findById(itemReq.productId())
                        .orElseThrow(() -> new EntityNotFoundException("Product", itemReq.productId()));
                item.setProduct(product);

                item.getQuantity().setValue(itemReq.quantity());
                String ccy = StringUtils.hasText(itemReq.currency()) ? itemReq.currency() : "TRL";
                item.getUnitPrice().setValue(itemReq.unitPrice());
                item.getUnitPrice().setCurrency(ccy);
                item.getUnitPrice().setLocalAmount(itemReq.unitPrice());

                BigDecimal amount = itemReq.quantity().multiply(itemReq.unitPrice())
                        .setScale(2, RoundingMode.HALF_UP);
                item.getAmount().setValue(amount);
                item.getAmount().setCurrency(ccy);
                item.getAmount().setLocalAmount(amount);

                if (itemReq.taxRate() != null) {
                    item.getTax().setRate(itemReq.taxRate());
                }

                item.setInfo(itemReq.info());
                order.getItems().add(item);
            }
        }

        order = orderNoteRepository.save(order);
        return toDetailDTOWithTotals(order);
    }

    public void delete(Long id) {
        if (!orderNoteRepository.existsById(id)) {
            throw new EntityNotFoundException("OrderNote", id);
        }
        orderNoteRepository.deleteById(id);
    }

    /**
     * Converts an order to an invoice.
     * Maps order fields and items to InvoiceSaveRequest, creates invoice via
     * InvoiceService.
     * The order's info field is updated with a reference to the created invoice.
     */
    public InvoiceDetailDTO convertToInvoice(Long orderId) {
        OrderNote order = orderNoteRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("OrderNote", orderId));

        // Map order items → invoice items
        List<InvoiceItemSaveRequest> invoiceItems = new ArrayList<>();
        if (order.getItems() != null) {
            for (OrderItem oi : order.getItems()) {
                String ccy = oi.getAmount() != null ? oi.getAmount().getCurrency() : "TRL";
                BigDecimal qty = oi.getQuantity() != null ? oi.getQuantity().getValue() : BigDecimal.ONE;
                BigDecimal price = oi.getUnitPrice() != null ? oi.getUnitPrice().getValue() : BigDecimal.ZERO;
                BigDecimal taxRate = oi.getTax() != null ? oi.getTax().getRate() : null;
                Long productId = oi.getProduct() != null ? oi.getProduct().getId() : null;

                invoiceItems.add(new InvoiceItemSaveRequest(
                        null, // id (new item)
                        productId,
                        qty,
                        null, // unitId
                        price,
                        ccy,
                        taxRate,
                        null, // discountRate
                        oi.getInfo()));
            }
        }

        // Determine trade action for invoice (Purchase Order → Purchase Invoice, Sale
        // Order → Sale Invoice)
        String tradeAction = order.getAction() != null ? order.getAction().name() : "Sale";

        // Build invoice save request
        InvoiceSaveRequest invoiceRequest = new InvoiceSaveRequest(
                null, // id (new invoice)
                tradeAction, // tradeAction
                null, // serial (auto-generated)
                order.getReference(), // reference from order
                LocalDate.now(), // invoice date = today
                order.getContact() != null ? order.getContact().getId() : null,
                order.getWarehouse() != null ? order.getWarehouse().getId() : null,
                null, // accountId
                "Sipariş #" + order.getCode() + " üzerinden oluşturuldu", // info
                invoiceItems);

        // Create the invoice
        InvoiceDetailDTO invoice = invoiceService.save(invoiceRequest);

        // Mark the order as converted
        String existingInfo = order.getInfo() != null ? order.getInfo() : "";
        order.setInfo(existingInfo + (existingInfo.isEmpty() ? "" : " | ")
                + "Faturaya dönüştürüldü (Fatura ID: " + invoice.id() + ")");
        orderNoteRepository.save(order);

        return invoice;
    }

    /**
     * OrderNote entity does not have embedded grandTotal fields,
     * so we calculate totals from items and enrich the DTO.
     */
    private OrderListDTO toListDTOWithTotals(OrderNote entity) {
        OrderListDTO base = orderMapper.toListDTO(entity);
        BigDecimal[] totals = calculateTotals(entity);
        String ccy = getItemsCurrency(entity);
        return new OrderListDTO(
                base.id(), base.code(), base.serial(), base.reference(),
                base.date(), base.tradeAction(), base.orderStatus(),
                base.contactCode(), base.contactFullname(),
                totals[0], ccy, base.info());
    }

    private OrderDetailDTO toDetailDTOWithTotals(OrderNote entity) {
        OrderDetailDTO base = orderMapper.toDetailDTO(entity);
        BigDecimal[] totals = calculateTotals(entity);
        String ccy = getItemsCurrency(entity);
        return new OrderDetailDTO(
                base.id(), base.code(), base.serial(), base.reference(),
                base.date(), base.tradeAction(), base.orderStatus(),
                base.contactId(), base.contactCode(), base.contactFullname(),
                base.warehouseId(), base.warehouseName(),
                new com.ut.tekir.common.dto.MoneySetDTO(ccy, totals[0], totals[0]),
                base.items(), base.info(),
                base.createDate(), base.createUser(), base.updateDate());
    }

    private BigDecimal[] calculateTotals(OrderNote entity) {
        BigDecimal grandTotal = BigDecimal.ZERO;
        if (entity.getItems() != null) {
            for (OrderItem item : entity.getItems()) {
                BigDecimal amount = item.getAmount() != null && item.getAmount().getValue() != null
                        ? item.getAmount().getValue()
                        : BigDecimal.ZERO;
                BigDecimal taxRate = item.getTax() != null && item.getTax().getRate() != null
                        ? item.getTax().getRate()
                        : BigDecimal.ZERO;
                BigDecimal tax = amount.multiply(taxRate)
                        .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                grandTotal = grandTotal.add(amount).add(tax);
            }
        }
        return new BigDecimal[] { grandTotal };
    }

    private String getItemsCurrency(OrderNote entity) {
        if (entity.getItems() != null && !entity.getItems().isEmpty()) {
            OrderItem first = entity.getItems().get(0);
            if (first.getAmount() != null && first.getAmount().getCurrency() != null) {
                return first.getAmount().getCurrency();
            }
        }
        return "TRL";
    }
}
