package com.ut.tekir.common.entity;

import com.ut.tekir.common.entity.AuditBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "PROMISSORY_PAYROLL_DETAIL")
@Getter
@Setter
public class PromissoryPayrollDetail extends AuditBase {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private PromissoryPayroll owner;

    @ManyToOne
    @JoinColumn(name = "PROMISSORY_NOTE_ID")
    private PromissoryNote promissoryNote;
    
    @Column(name = "INFO")
    private String info;

}
