package com.ut.tekir.repository;

import com.ut.tekir.common.entity.QuickLaunch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface QuickLaunchRepository extends JpaRepository<QuickLaunch, Long>, JpaSpecificationExecutor<QuickLaunch> {
}
