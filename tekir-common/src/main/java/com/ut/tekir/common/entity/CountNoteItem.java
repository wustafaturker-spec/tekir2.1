package com.ut.tekir.common.entity;

import com.ut.tekir.common.entity.AuditBase;
import com.ut.tekir.common.embeddable.Quantity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "COUNT_NOTE_ITEM")
@Getter
@Setter
public class CountNoteItem extends AuditBase {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private CountNote owner;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;
    
    @Column(name = "COUNT_QTY")
    private Integer countQuantity = 0; // Or Quantity embeddable if unit needed. Using int for now as simple count.
    
    @Column(name = "EXISTING_QTY")
    private Integer existingQuantity = 0;

    @Column(name = "LINE_CODE")
    private String lineCode;
    
    @Column(name = "INFO")
    private String info;
}
