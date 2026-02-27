package com.ut.tekir.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ut.tekir.common.entity.Currency;
import com.ut.tekir.common.entity.CurrencyRate;
import com.ut.tekir.common.exception.EntityNotFoundException;
import com.ut.tekir.repository.CurrencyRateRepository;
import com.ut.tekir.repository.CurrencyRepository;

import lombok.RequiredArgsConstructor;

/**
 * Currency service — replaces legacy CurrencyHomeBean.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final CurrencyRateRepository currencyRateRepository;

    @Transactional(readOnly = true)
    public List<Currency> findAll() {
        return currencyRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Currency> findAllActive() {
        return currencyRepository.findAll((root, query, cb) ->
            cb.isTrue(root.get("active"))
        );
    }

    @Transactional(readOnly = true)
    public Currency getById(Long id) {
        return currencyRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Currency", id));
    }

    @Transactional(readOnly = true)
    public Currency getByCode(String code) {
        return currencyRepository.findByCode(code)
            .orElseThrow(() -> new EntityNotFoundException("Currency", code));
    }

    public Currency save(Currency currency) {
        return currencyRepository.save(currency);
    }

    public void delete(Long id) {
        if (!currencyRepository.existsById(id)) {
            throw new EntityNotFoundException("Currency", id);
        }
        currencyRepository.deleteById(id);
    }
}
