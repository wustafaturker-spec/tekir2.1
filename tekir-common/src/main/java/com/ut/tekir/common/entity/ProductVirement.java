package com.ut.tekir.common.entity;

import com.ut.tekir.common.entity.DocumentBase;
import com.ut.tekir.common.enums.DocumentType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PRODUCT_VIREMENT")
@Getter
@Setter
public class ProductVirement extends DocumentBase {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "WAREHOUSE_ID")
    private Warehouse warehouse;
    
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductVirementItem> items = new ArrayList<>();

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public DocumentType getDocumentType() {
        return DocumentType.ProductVirement; // Ensure Exists
    }
}
