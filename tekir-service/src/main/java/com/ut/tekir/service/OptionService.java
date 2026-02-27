package com.ut.tekir.service;

import com.ut.tekir.common.entity.Option;
import com.ut.tekir.repository.OptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OptionService {

    private final OptionRepository optionRepository;

    @Cacheable(value = "options", key = "#key")
    public String getOption(String key) {
        return optionRepository.findByKey(key)
                .map(Option::getValue)
                .orElse(null);
    }

    public String getOption(String key, String defaultValue) {
        String val = getOption(key);
        return val != null ? val : defaultValue;
    }

    @Transactional
    @CacheEvict(value = "options", key = "#key")
    public void saveOption(String key, String value) {
        Option option = optionRepository.findByKey(key)
                .orElseGet(() -> {
                    Option o = new Option();
                    o.setKey(key);
                    return o;
                });
        option.setValue(value);
        optionRepository.save(option);
    }
}
