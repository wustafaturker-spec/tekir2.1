package com.ut.tekir.service.mapper;

import com.ut.tekir.common.dto.MoneySetDTO;
import com.ut.tekir.common.dto.documentmatch.DocumentMatchListDTO;
import com.ut.tekir.common.embeddable.MoneySet;
import com.ut.tekir.common.entity.DocumentMatch;
import java.math.BigDecimal;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-26T21:57:49+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class DocumentMatchMapperImpl implements DocumentMatchMapper {

    @Override
    public DocumentMatchListDTO toListDTO(DocumentMatch entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        Long sourceDocumentId = null;
        Long targetDocumentId = null;
        MoneySetDTO amount = null;

        id = entity.getId();
        sourceDocumentId = entity.getSourceDocumentId();
        targetDocumentId = entity.getTargetDocumentId();
        amount = moneySetToMoneySetDTO( entity.getAmount() );

        String sourceDocumentType = entity.getSourceDocumentType() != null ? entity.getSourceDocumentType().name() : null;
        String targetDocumentType = entity.getTargetDocumentType() != null ? entity.getTargetDocumentType().name() : null;

        DocumentMatchListDTO documentMatchListDTO = new DocumentMatchListDTO( id, sourceDocumentType, sourceDocumentId, targetDocumentType, targetDocumentId, amount );

        return documentMatchListDTO;
    }

    protected MoneySetDTO moneySetToMoneySetDTO(MoneySet moneySet) {
        if ( moneySet == null ) {
            return null;
        }

        String currency = null;
        BigDecimal value = null;
        BigDecimal localAmount = null;

        currency = moneySet.getCurrency();
        value = moneySet.getValue();
        localAmount = moneySet.getLocalAmount();

        MoneySetDTO moneySetDTO = new MoneySetDTO( currency, value, localAmount );

        return moneySetDTO;
    }
}
