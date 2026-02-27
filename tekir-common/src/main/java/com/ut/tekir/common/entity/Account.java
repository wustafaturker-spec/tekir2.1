package com.ut.tekir.common.entity;

import com.ut.tekir.common.entity.AuditBase;
import com.ut.tekir.common.enums.AccountType;
// import com.ut.tekir.common.enums.Currency; // Removed, using String for ISO code
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a financial account (Cash Box or Bank Account).
 */
@Entity
@Table(name = "ACCOUNT")
@Getter
@Setter
public class Account extends AuditBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CODE", length = 20, nullable = false, unique = true)
    @NotNull
    @Size(min = 1, max = 20)
    private String code;

    @Column(name = "NAME", length = 50)
    @Size(max = 50)
    private String name;

    @Column(name = "INFO")
    private String info;

    @Column(name = "ACCOUNT_TYPE")
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(name = "CCY", length = 3)
    private String currency;

    @ManyToOne
    @JoinColumn(name = "ORGANIZATION_ID")
    private Organization organization;

}
