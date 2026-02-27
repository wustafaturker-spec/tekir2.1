package com.ut.tekir.common.entity;

import com.ut.tekir.common.entity.AuditBase;
import com.ut.tekir.common.embeddable.MoneySet;
import com.ut.tekir.common.embeddable.TaxEmbeddable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "EXPENSE_ITEM")
@Getter
@Setter
public class ExpenseItem extends AuditBase {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private ExpenseNote owner;

    // Service/Expense definition usually goes here, but for now just description or Service product
    // Assuming Product can be service
    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product service; 
    
    @Embedded
    private MoneySet amount = new MoneySet();
    
    @Embedded
    private TaxEmbeddable tax = new TaxEmbeddable();
    
    @Column(name = "INFO")
    private String info;

}

