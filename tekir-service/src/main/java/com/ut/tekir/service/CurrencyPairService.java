package com.ut.tekir.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ut.tekir.common.entity.CurrencyPair;
import com.ut.tekir.common.exception.EntityNotFoundException;
import com.ut.tekir.repository.CurrencyPairRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CurrencyPairService {

    private final CurrencyPairRepository currencyPairRepository;

    @Transactional(readOnly = true)
    public List<CurrencyPair> findAll() {
        return currencyPairRepository.findAll();
    }

    @Transactional(readOnly = true)
    public CurrencyPair getById(Long id) {
        return currencyPairRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("CurrencyPair", id));
    }

    public CurrencyPair save(CurrencyPair pair) {
        return currencyPairRepository.save(pair);
    }

    public void delete(Long id) {
        if (!currencyPairRepository.existsById(id)) {
            throw new EntityNotFoundException("CurrencyPair", id);
        }
        currencyPairRepository.deleteById(id);
    }
}
