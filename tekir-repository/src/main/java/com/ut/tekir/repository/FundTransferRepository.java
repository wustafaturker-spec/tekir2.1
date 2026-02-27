package com.ut.tekir.repository;

import com.ut.tekir.common.entity.FundTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FundTransferRepository extends JpaRepository<FundTransfer, Long> {
}
