package com.ut.tekir.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ut.tekir.common.dto.payment.PaymentListDTO;
import com.ut.tekir.common.enums.FinanceAction;
import com.ut.tekir.repository.PaymentRepository;
import com.ut.tekir.service.mapper.PaymentMapper;

import lombok.RequiredArgsConstructor;

/**
 * Collection (Tahsilat) service — filters payments with Credit action.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class CollectionService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    @Transactional(readOnly = true)
    public Page<PaymentListDTO> findCollections(Pageable pageable) {
        return paymentRepository.findAll(
            (root, query, cb) -> cb.equal(root.get("action"), FinanceAction.Credit),
            pageable
        ).map(paymentMapper::toListDTO);
    }
}
