package com.ut.tekir.repository;
import com.ut.tekir.common.entity.OptionDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
@Repository public interface OptionDefinitionRepository extends JpaRepository<OptionDefinition, Long>, JpaSpecificationExecutor<OptionDefinition> {}
