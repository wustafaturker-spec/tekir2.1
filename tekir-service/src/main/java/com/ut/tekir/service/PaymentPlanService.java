package com.ut.tekir.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ut.tekir.common.entity.PaymentPlan;
import com.ut.tekir.common.exception.EntityNotFoundException;
import com.ut.tekir.repository.PaymentPlanRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentPlanService {

    private final PaymentPlanRepository paymentPlanRepository;

    @Transactional(readOnly = true)
    public Page<PaymentPlan> findAll(Pageable pageable) {
        return paymentPlanRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public PaymentPlan getById(Long id) {
        return paymentPlanRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("PaymentPlan", id));
    }

    public PaymentPlan save(PaymentPlan plan) {
        return paymentPlanRepository.save(plan);
    }

    public void delete(Long id) {
        if (!paymentPlanRepository.existsById(id)) {
            throw new EntityNotFoundException("PaymentPlan", id);
        }
        paymentPlanRepository.deleteById(id);
    }
}
