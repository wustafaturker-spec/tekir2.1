package com.ut.tekir.repository;
import com.ut.tekir.common.entity.DebitCreditVirement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
@Repository public interface DebitCreditVirementRepository extends JpaRepository<DebitCreditVirement, Long>, JpaSpecificationExecutor<DebitCreditVirement> {}
