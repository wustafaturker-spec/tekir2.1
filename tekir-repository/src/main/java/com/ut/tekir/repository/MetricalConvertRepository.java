package com.ut.tekir.repository;

import com.ut.tekir.common.entity.MetricalConvert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MetricalConvertRepository extends JpaRepository<MetricalConvert, Long>, JpaSpecificationExecutor<MetricalConvert> {
}
