package com.ut.tekir.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ut.tekir.common.entity.PaymentCommission;
import com.ut.tekir.common.exception.EntityNotFoundException;
import com.ut.tekir.repository.PaymentCommissionRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentCommissionService {

    private final PaymentCommissionRepository paymentCommissionRepository;

    @Transactional(readOnly = true)
    public List<PaymentCommission> findAll() {
        return paymentCommissionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public PaymentCommission getById(Long id) {
        return paymentCommissionRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("PaymentCommission", id));
    }

    public PaymentCommission save(PaymentCommission commission) {
        return paymentCommissionRepository.save(commission);
    }

    public void delete(Long id) {
        if (!paymentCommissionRepository.existsById(id)) {
            throw new EntityNotFoundException("PaymentCommission", id);
        }
        paymentCommissionRepository.deleteById(id);
    }
}
