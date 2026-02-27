package com.ut.tekir.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ut.tekir.common.entity.CurrencyPair;
import com.ut.tekir.common.entity.CurrencyRate;
import com.ut.tekir.common.exception.EntityNotFoundException;
import com.ut.tekir.repository.CurrencyPairRepository;
import com.ut.tekir.repository.CurrencyRateRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CurrencyRateService {

    private final CurrencyRateRepository currencyRateRepository;
    private final CurrencyPairRepository currencyPairRepository;

    @Transactional(readOnly = true)
    public List<CurrencyRate> findAll() {
        return currencyRateRepository.findAll();
    }

    @Transactional(readOnly = true)
    public CurrencyRate getById(Long id) {
        return currencyRateRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("CurrencyRate", id));
    }

    @Transactional(readOnly = true)
    public List<CurrencyRate> findByPairAndDate(Long pairId, LocalDate date) {
        CurrencyPair pair = currencyPairRepository.findById(pairId)
            .orElseThrow(() -> new EntityNotFoundException("CurrencyPair", pairId));
        return currencyRateRepository.findByCurrencyPairAndDate(pair, date);
    }

    @Transactional(readOnly = true)
    public Optional<CurrencyRate> findLatest(Long pairId) {
        CurrencyPair pair = currencyPairRepository.findById(pairId)
            .orElseThrow(() -> new EntityNotFoundException("CurrencyPair", pairId));
        return currencyRateRepository.findFirstByCurrencyPairOrderByDateDesc(pair);
    }

    public CurrencyRate save(CurrencyRate rate) {
        return currencyRateRepository.save(rate);
    }

    public void delete(Long id) {
        if (!currencyRateRepository.existsById(id)) {
            throw new EntityNotFoundException("CurrencyRate", id);
        }
        currencyRateRepository.deleteById(id);
    }
}
