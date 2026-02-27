package com.ut.tekir.repository;
import com.ut.tekir.common.entity.LogoImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
@Repository public interface LogoImageRepository extends JpaRepository<LogoImage, Long>, JpaSpecificationExecutor<LogoImage> {}
