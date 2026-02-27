package com.ut.tekir.repository;
import com.ut.tekir.common.entity.PortfolioToPortfolioTransferItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
@Repository public interface PortfolioToPortfolioTransferItemRepository extends JpaRepository<PortfolioToPortfolioTransferItem, Long>, JpaSpecificationExecutor<PortfolioToPortfolioTransferItem> {}
