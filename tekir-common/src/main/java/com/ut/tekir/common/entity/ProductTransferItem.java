package com.ut.tekir.common.entity;

import com.ut.tekir.common.entity.AuditBase;
import com.ut.tekir.common.embeddable.Quantity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "PRODUCT_TRANSFER_ITEM")
@Getter
@Setter
public class ProductTransferItem extends AuditBase {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private ProductTransfer owner;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;
    
    @Embedded
    private Quantity quantity = new Quantity();
    
    @Column(name = "LINE_CODE")
    private String lineCode;
    
    @Column(name = "INFO")
    private String info;

}
