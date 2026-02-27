package com.ut.tekir.common.entity;

import com.ut.tekir.common.entity.AuditBase;
import com.ut.tekir.common.embeddable.Quantity;
import com.ut.tekir.common.embeddable.MoneySet;
import com.ut.tekir.common.enums.FinanceAction; // In/Out
import com.ut.tekir.common.enums.DocumentType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "PRODUCT_TXN")
@Getter
@Setter
public class ProductTxn extends AuditBase {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "WAREHOUSE_ID")
    private Warehouse warehouse;

    @Embedded
    private Quantity quantity = new Quantity();

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "FINANCE_ACTION")
    private FinanceAction action; 
    
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "DOCUMENT_TYPE")
    private DocumentType documentType;
    
    @Column(name = "DOCUMENT_ID")
    private Long documentId;
    
    @Column(name = "SERIAL", length = 20)
    private String serial;
    
    @Column(name = "REFERENCE", length = 20)
    private String reference;
    
    @Column(name = "TXN_DATE")
    private LocalDate date;
    
    @Column(name = "INFO")
    private String info;
    
    @Column(name = "UNIT_PRICE")
    private BigDecimal unitPrice = BigDecimal.ZERO; 

}


