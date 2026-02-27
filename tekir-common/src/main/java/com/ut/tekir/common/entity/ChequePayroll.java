package com.ut.tekir.common.entity;

import com.ut.tekir.common.entity.DocumentBase;
import com.ut.tekir.common.enums.DocumentType;
import com.ut.tekir.common.enums.PayrollType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CHEQUE_PAYROLL")
@Getter
@Setter
public class ChequePayroll extends DocumentBase {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "PAYROLL_TYPE")
    private PayrollType payrollType;

    @ManyToOne
    @JoinColumn(name = "CONTACT_ID")
    private Contact contact; // For Client/Vendor

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account; // For Bank/Cash
    
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChequePayrollDetail> items = new ArrayList<>();

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public DocumentType getDocumentType() {
        return DocumentType.ChequePaymentPayroll;
    }
}

