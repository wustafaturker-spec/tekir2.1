package com.ut.tekir.api.controller;

import com.ut.tekir.common.entity.City;
import com.ut.tekir.common.entity.Country;
import com.ut.tekir.common.entity.Province;
import com.ut.tekir.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Locations", description = "Ülke/Şehir/İlçe tanımları")
public class LocationController {

    private final LocationService locationService;

    // --- Countries ---

    @GetMapping("/countries")
    @Operation(summary = "Ülke listesi")
    @PreAuthorize("hasAuthority('location:read')")
    public List<Country> listCountries() {
        return locationService.findAllCountries();
    }

    @GetMapping("/countries/{id}")
    @Operation(summary = "Ülke detayı")
    @PreAuthorize("hasAuthority('location:read')")
    public Country getCountry(@PathVariable Long id) {
        return locationService.getCountryById(id);
    }

    @PostMapping("/countries")
    @Operation(summary = "Yeni ülke")
    @PreAuthorize("hasAuthority('location:create')")
    public Country createCountry(@Valid @RequestBody Country country) {
        return locationService.saveCountry(country);
    }

    @PutMapping("/countries/{id}")
    @Operation(summary = "Ülke güncelle")
    @PreAuthorize("hasAuthority('location:update')")
    public Country updateCountry(@PathVariable Long id, @Valid @RequestBody Country country) {
        country.setId(id);
        return locationService.saveCountry(country);
    }

    @DeleteMapping("/countries/{id}")
    @Operation(summary = "Ülke sil")
    @PreAuthorize("hasAuthority('location:delete')")
    public void deleteCountry(@PathVariable Long id) {
        locationService.deleteCountry(id);
    }

    // --- Cities ---

    @GetMapping("/cities")
    @Operation(summary = "Şehir listesi")
    @PreAuthorize("hasAuthority('location:read')")
    public List<City> listCities() {
        return locationService.findActiveCities();
    }

    @GetMapping("/cities/{id}")
    @Operation(summary = "Şehir detayı")
    @PreAuthorize("hasAuthority('location:read')")
    public City getCity(@PathVariable Long id) {
        return locationService.getCityById(id);
    }

    @PostMapping("/cities")
    @Operation(summary = "Yeni şehir")
    @PreAuthorize("hasAuthority('location:create')")
    public City createCity(@Valid @RequestBody City city) {
        return locationService.saveCity(city);
    }

    @PutMapping("/cities/{id}")
    @Operation(summary = "Şehir güncelle")
    @PreAuthorize("hasAuthority('location:update')")
    public City updateCity(@PathVariable Long id, @Valid @RequestBody City city) {
        city.setId(id);
        return locationService.saveCity(city);
    }

    @DeleteMapping("/cities/{id}")
    @Operation(summary = "Şehir sil")
    @PreAuthorize("hasAuthority('location:delete')")
    public void deleteCity(@PathVariable Long id) {
        locationService.deleteCity(id);
    }

    // --- Provinces ---

    @GetMapping("/cities/{cityId}/provinces")
    @Operation(summary = "Şehre ait ilçeler")
    @PreAuthorize("hasAuthority('location:read')")
    public List<Province> listProvinces(@PathVariable Long cityId) {
        return locationService.findProvincesByCityId(cityId);
    }

    @PostMapping("/cities/{cityId}/provinces")
    @Operation(summary = "İlçe ekle")
    @PreAuthorize("hasAuthority('location:create')")
    public Province createProvince(@PathVariable Long cityId, @Valid @RequestBody Province province) {
        City city = locationService.getCityById(cityId);
        province.setCity(city);
        return locationService.saveProvince(province);
    }

    @DeleteMapping("/provinces/{id}")
    @Operation(summary = "İlçe sil")
    @PreAuthorize("hasAuthority('location:delete')")
    public void deleteProvince(@PathVariable Long id) {
        locationService.deleteProvince(id);
    }
}
