package com.ut.tekir.common.entity;

import com.ut.tekir.common.embeddable.MoneySet;
import com.ut.tekir.common.enums.DocumentType;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Document match entity for linking related documents.
 */
@Entity
@Table(name = "DOCUMENT_MATCH")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class DocumentMatch extends AuditBase {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @Column(name = "SOURCE_DOCUMENT_TYPE")
    @Enumerated(EnumType.ORDINAL)
    private DocumentType sourceDocumentType;

    @Column(name = "SOURCE_DOCUMENT_ID")
    private Long sourceDocumentId;

    @Column(name = "TARGET_DOCUMENT_TYPE")
    @Enumerated(EnumType.ORDINAL)
    private DocumentType targetDocumentType;

    @Column(name = "TARGET_DOCUMENT_ID")
    private Long targetDocumentId;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "value", column = @Column(name = "AMOUNT_VALUE")),
        @AttributeOverride(name = "currency", column = @Column(name = "AMOUNT_CCY")),
        @AttributeOverride(name = "localAmount", column = @Column(name = "AMOUNT_LCYVAL"))
    })
    private MoneySet amount = new MoneySet();
}
