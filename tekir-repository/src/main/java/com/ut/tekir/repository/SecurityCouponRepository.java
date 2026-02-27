package com.ut.tekir.repository;
import com.ut.tekir.common.entity.SecurityCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
@Repository public interface SecurityCouponRepository extends JpaRepository<SecurityCoupon, Long>, JpaSpecificationExecutor<SecurityCoupon> {}
