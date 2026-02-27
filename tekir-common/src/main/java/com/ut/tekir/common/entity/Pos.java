package com.ut.tekir.common.entity;

import com.ut.tekir.common.entity.AuditBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "POS")
@Getter
@Setter
public class Pos extends AuditBase {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "BANK_ACCOUNT_ID")
    private BankAccount bankAccount; // Linked Bank Account

    @Column(name = "CODE", length = 20, nullable = false, unique = true)
    private String code;

    @Column(name = "NAME", length = 50)
    private String name;
    
    @Column(name = "ISACTIVE")
    private Boolean active = Boolean.TRUE;

}
