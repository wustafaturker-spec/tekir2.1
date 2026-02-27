package com.ut.tekir.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ut.tekir.common.entity.PriceList;
import com.ut.tekir.common.entity.PriceListItem;
import com.ut.tekir.common.exception.EntityNotFoundException;
import com.ut.tekir.repository.PriceListRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PriceItemService {

    private final PriceListRepository priceListRepository;

    @Transactional(readOnly = true)
    public List<PriceListItem> findByPriceListId(Long priceListId) {
        PriceList priceList = priceListRepository.findById(priceListId)
            .orElseThrow(() -> new EntityNotFoundException("PriceList", priceListId));
        return priceList.getItems();
    }

    public PriceList addItem(Long priceListId, PriceListItem item) {
        PriceList priceList = priceListRepository.findById(priceListId)
            .orElseThrow(() -> new EntityNotFoundException("PriceList", priceListId));
        item.setOwner(priceList);
        priceList.getItems().add(item);
        return priceListRepository.save(priceList);
    }

    public void removeItem(Long priceListId, Long itemId) {
        PriceList priceList = priceListRepository.findById(priceListId)
            .orElseThrow(() -> new EntityNotFoundException("PriceList", priceListId));
        priceList.getItems().removeIf(item -> item.getId().equals(itemId));
        priceListRepository.save(priceList);
    }
}
