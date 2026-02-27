package com.ut.tekir.common.entity;

import com.ut.tekir.common.entity.AuditBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "BANK_BRANCH")
@Getter
@Setter
public class BankBranch extends AuditBase {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "BANK_ID")
    private Bank bank;

    @Column(name = "CODE", length = 20, nullable = false)
    private String code;

    @Column(name = "NAME", length = 50)
    private String name;

    @Column(name = "EFT_CODE", length = 20)
    private String eftCode;
    
    @Column(name = "ISACTIVE")
    private Boolean active = Boolean.TRUE;

}
