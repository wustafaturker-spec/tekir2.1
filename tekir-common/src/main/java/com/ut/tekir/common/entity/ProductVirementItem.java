package com.ut.tekir.common.entity;

import com.ut.tekir.common.entity.AuditBase;
import com.ut.tekir.common.embeddable.Quantity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "PRODUCT_VIREMENT_ITEM")
@Getter
@Setter
public class ProductVirementItem extends AuditBase {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private ProductVirement owner;

    @ManyToOne
    @JoinColumn(name = "FROM_PRODUCT_ID")
    private Product fromProduct;
    
    @ManyToOne
    @JoinColumn(name = "TO_PRODUCT_ID")
    private Product toProduct;
    
    @Embedded
    private Quantity quantity = new Quantity();
    
    @Column(name = "INFO")
    private String info;

}
