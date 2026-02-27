package com.ut.tekir.repository;
import com.ut.tekir.common.entity.DebitCreditVirementItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
@Repository public interface DebitCreditVirementItemRepository extends JpaRepository<DebitCreditVirementItem, Long>, JpaSpecificationExecutor<DebitCreditVirementItem> {}
