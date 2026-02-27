package com.ut.tekir.common.entity;

import com.ut.tekir.common.entity.AuditBase;
import com.ut.tekir.common.embeddable.MoneySet;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "DEBIT_CREDIT_NOTE_ITEM")
@Getter
@Setter
public class DebitCreditNoteItem extends AuditBase {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private DebitCreditNote owner;

    @Embedded
    private MoneySet amount = new MoneySet();
    
    @Column(name = "INFO")
    private String info;
    
    @Column(name = "LINE_CODE")
    private String lineCode;

}
