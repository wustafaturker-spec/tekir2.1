package com.ut.tekir.service.mapper;

import com.ut.tekir.common.dto.debitcredit.DebitCreditVirementDetailDTO;
import com.ut.tekir.common.dto.debitcredit.DebitCreditVirementListDTO;
import com.ut.tekir.common.entity.DebitCreditVirement;
import com.ut.tekir.common.entity.DebitCreditVirementItem;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DebitCreditVirementMapper {

    DebitCreditVirementListDTO toListDTO(DebitCreditVirement entity);

    DebitCreditVirementDetailDTO toDetailDTO(DebitCreditVirement entity);

    DebitCreditVirementDetailDTO.VirementItemDTO toItemDTO(DebitCreditVirementItem item);
    List<DebitCreditVirementDetailDTO.VirementItemDTO> toItemDTOList(List<DebitCreditVirementItem> items);
}
