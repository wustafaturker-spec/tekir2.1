package com.ut.tekir.common.entity;

import com.ut.tekir.common.enums.DocumentType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Portfolio to Portfolio Transfer document.
 */
@Entity
@Table(name = "PORTF_TO_PORTF_TRANSFER")
@Getter
@Setter
public class PortfolioToPortfolioTransfer extends DocumentBase {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "FROM_PORTFOLIO_ID")
    private Portfolio fromPortfolio;

    @ManyToOne
    @JoinColumn(name = "TO_PORTFOLIO_ID")
    private Portfolio toPortfolio;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PortfolioToPortfolioTransferItem> items = new ArrayList<>();

    @Override
    public Long getId() { return id; }

    @Override
    public DocumentType getDocumentType() { return DocumentType.PorfolioToPortfolioTransfer; }
}
