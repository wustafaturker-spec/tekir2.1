package com.ut.tekir.service.mapper;

import com.ut.tekir.common.dto.cheque.ChequeDetailDTO;
import com.ut.tekir.common.dto.cheque.ChequeListDTO;
import com.ut.tekir.common.entity.Cheque;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * MapStruct mapper for Cheque entity ↔ DTO conversions.
 * Note: Cheque entity stores bank info as String fields, not entity references.
 */
@Mapper(componentModel = "spring")
public interface ChequeMapper {

    @Mapping(target = "branchName", source = "bankBranch")
    @Mapping(target = "status", expression = "java(entity.getStatus() != null ? entity.getStatus().name() : null)")
    @Mapping(target = "amount", source = "amount.value")
    @Mapping(target = "currency", source = "amount.currency")
    @Mapping(target = "dueDate", source = "maturityDate")
    @Mapping(target = "contactFullname", source = "contact.name")
    @Mapping(target = "referenceNo", source = "code")
    @Mapping(target = "direction", source = "direction")
    @Mapping(target = "chequeNumber", source = "reference") // Map Document.reference to ChequeNumber
    ChequeListDTO toListDTO(Cheque entity);

    @Mapping(target = "referenceNo", source = "code")
    @Mapping(target = "branchName", source = "bankBranch")
    @Mapping(target = "status", expression = "java(entity.getStatus() != null ? entity.getStatus().name() : null)")
    @Mapping(target = "money", source = "amount")
    @Mapping(target = "dueDate", source = "maturityDate")
    @Mapping(target = "chequeNumber", source = "reference") // Map Document.reference to ChequeNumber
    @Mapping(target = "issueDate", ignore = true)
    @Mapping(target = "bankId", ignore = true)
    @Mapping(target = "bankBranchId", ignore = true)
    @Mapping(target = "contactId", source = "contact.id")
    @Mapping(target = "contactCode", ignore = true)
    @Mapping(target = "contactFullname", ignore = true)
    ChequeDetailDTO toDetailDTO(Cheque entity);
}
