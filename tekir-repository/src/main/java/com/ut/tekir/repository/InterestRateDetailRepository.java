package com.ut.tekir.repository;
import com.ut.tekir.common.entity.InterestRateDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
@Repository public interface InterestRateDetailRepository extends JpaRepository<InterestRateDetail, Long>, JpaSpecificationExecutor<InterestRateDetail> {}
