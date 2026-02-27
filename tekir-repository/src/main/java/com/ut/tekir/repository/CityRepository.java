package com.ut.tekir.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.ut.tekir.common.entity.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long>, JpaSpecificationExecutor<City> {
    Optional<City> findByCode(String code);
    Optional<City> findByPlate(String plate);
    List<City> findByActiveTrue();
}
