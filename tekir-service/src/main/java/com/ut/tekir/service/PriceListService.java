package com.ut.tekir.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ut.tekir.common.entity.PriceList;
import com.ut.tekir.common.exception.EntityNotFoundException;
import com.ut.tekir.repository.PriceListRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PriceListService {

    private final PriceListRepository priceListRepository;

    @Transactional(readOnly = true)
    public Page<PriceList> findAll(Pageable pageable) {
        return priceListRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public PriceList getById(Long id) {
        return priceListRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("PriceList", id));
    }

    public PriceList save(PriceList priceList) {
        return priceListRepository.save(priceList);
    }

    public void delete(Long id) {
        if (!priceListRepository.existsById(id)) {
            throw new EntityNotFoundException("PriceList", id);
        }
        priceListRepository.deleteById(id);
    }
}
