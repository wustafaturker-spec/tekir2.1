package com.ut.tekir.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ut.tekir.common.entity.City;
import com.ut.tekir.common.entity.Country;
import com.ut.tekir.common.entity.Province;
import com.ut.tekir.common.exception.EntityNotFoundException;
import com.ut.tekir.repository.CityRepository;
import com.ut.tekir.repository.CountryRepository;
import com.ut.tekir.repository.ProvinceRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class LocationService {

    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;
    private final ProvinceRepository provinceRepository;

    // --- Country ---

    @Transactional(readOnly = true)
    public List<Country> findAllCountries() {
        return countryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Country getCountryById(Long id) {
        return countryRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Country", id));
    }

    public Country saveCountry(Country country) {
        return countryRepository.save(country);
    }

    public void deleteCountry(Long id) {
        if (!countryRepository.existsById(id)) {
            throw new EntityNotFoundException("Country", id);
        }
        countryRepository.deleteById(id);
    }

    // --- City ---

    @Transactional(readOnly = true)
    public List<City> findAllCities() {
        return cityRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<City> findActiveCities() {
        return cityRepository.findByActiveTrue();
    }

    @Transactional(readOnly = true)
    public City getCityById(Long id) {
        return cityRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("City", id));
    }

    public City saveCity(City city) {
        return cityRepository.save(city);
    }

    public void deleteCity(Long id) {
        if (!cityRepository.existsById(id)) {
            throw new EntityNotFoundException("City", id);
        }
        cityRepository.deleteById(id);
    }

    // --- Province ---

    @Transactional(readOnly = true)
    public List<Province> findProvincesByCityId(Long cityId) {
        City city = getCityById(cityId);
        return provinceRepository.findByCity(city);
    }

    @Transactional(readOnly = true)
    public Province getProvinceById(Long id) {
        return provinceRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Province", id));
    }

    public Province saveProvince(Province province) {
        return provinceRepository.save(province);
    }

    public void deleteProvince(Long id) {
        if (!provinceRepository.existsById(id)) {
            throw new EntityNotFoundException("Province", id);
        }
        provinceRepository.deleteById(id);
    }
}
