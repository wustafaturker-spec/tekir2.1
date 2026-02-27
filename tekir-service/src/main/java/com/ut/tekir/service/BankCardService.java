package com.ut.tekir.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ut.tekir.common.entity.BankCard;
import com.ut.tekir.common.exception.EntityNotFoundException;
import com.ut.tekir.repository.BankCardRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BankCardService {

    private final BankCardRepository bankCardRepository;

    @Transactional(readOnly = true)
    public List<BankCard> findAll() {
        return bankCardRepository.findAll();
    }

    @Transactional(readOnly = true)
    public BankCard getById(Long id) {
        return bankCardRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("BankCard", id));
    }

    public BankCard save(BankCard bankCard) {
        return bankCardRepository.save(bankCard);
    }

    public void delete(Long id) {
        if (!bankCardRepository.existsById(id)) {
            throw new EntityNotFoundException("BankCard", id);
        }
        bankCardRepository.deleteById(id);
    }
}
