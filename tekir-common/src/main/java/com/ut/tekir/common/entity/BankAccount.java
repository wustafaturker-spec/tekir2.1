package com.ut.tekir.common.entity;

import com.ut.tekir.common.entity.AuditBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "BANK_ACCOUNT")
@Getter
@Setter
public class BankAccount extends AuditBase {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "BANK_BRANCH_ID")
    private BankBranch bankBranch;

    @Column(name = "ACCOUNT_NO", length = 20)
    private String accountNo;

    @Column(name = "IBAN", length = 50)
    private String iban;

    @Column(name = "CCY", length = 3)
    private String currency; // ISO Code

    @Column(name = "NAME", length = 50)
    private String name;
    
    @OneToOne
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account; // Link to finance account

    @Column(name = "ISACTIVE")
    private Boolean active = Boolean.TRUE;

}
