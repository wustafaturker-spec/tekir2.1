package com.ut.tekir.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ut.tekir.common.entity.PaymentContract;
import com.ut.tekir.common.exception.EntityNotFoundException;
import com.ut.tekir.repository.PaymentContractRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentContractService {

    private final PaymentContractRepository paymentContractRepository;

    @Transactional(readOnly = true)
    public List<PaymentContract> findAll() {
        return paymentContractRepository.findAll();
    }

    @Transactional(readOnly = true)
    public PaymentContract getById(Long id) {
        return paymentContractRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("PaymentContract", id));
    }

    public PaymentContract save(PaymentContract contract) {
        return paymentContractRepository.save(contract);
    }

    public void delete(Long id) {
        if (!paymentContractRepository.existsById(id)) {
            throw new EntityNotFoundException("PaymentContract", id);
        }
        paymentContractRepository.deleteById(id);
    }
}
