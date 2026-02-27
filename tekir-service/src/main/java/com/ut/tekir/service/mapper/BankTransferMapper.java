package com.ut.tekir.service.mapper;

import com.ut.tekir.common.dto.banktransfer.BankTransferDetailDTO;
import com.ut.tekir.common.dto.banktransfer.BankTransferListDTO;
import com.ut.tekir.common.entity.BankToBankTransfer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BankTransferMapper {

    @Mapping(target = "transferType", expression = "java(entity.getTransferType() != null ? entity.getTransferType().name() : null)")
    BankTransferListDTO toListDTO(BankToBankTransfer entity);

    @Mapping(target = "transferType", expression = "java(entity.getTransferType() != null ? entity.getTransferType().name() : null)")
    BankTransferDetailDTO toDetailDTO(BankToBankTransfer entity);
}
