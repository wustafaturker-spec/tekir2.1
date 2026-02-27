package com.ut.tekir.common.entity;

import com.ut.tekir.common.entity.AuditBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "CHEQUE_PAYROLL_DETAIL")
@Getter
@Setter
public class ChequePayrollDetail extends AuditBase {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private ChequePayroll owner;

    @ManyToOne
    @JoinColumn(name = "CHEQUE_ID")
    private Cheque cheque;
    
    @Column(name = "INFO")
    private String info;

}
