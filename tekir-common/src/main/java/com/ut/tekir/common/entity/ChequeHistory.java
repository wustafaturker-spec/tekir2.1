package com.ut.tekir.common.entity;

import com.ut.tekir.common.entity.AuditBase;
import com.ut.tekir.common.enums.ChequeStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name = "CHEQUE_HISTORY")
@Getter
@Setter
public class ChequeHistory extends AuditBase {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private Cheque owner;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "STATUS")
    private ChequeStatus status;
    
    @Column(name = "PROCESS_DATE")
    private LocalDate processDate = LocalDate.now();
    
    @Column(name = "INFO")
    private String info;
    
    @Column(name = "SOURCE")
    private String source; // Source of change (e.g. Serial, User)

    @ManyToOne
    @JoinColumn(name = "CONTACT_ID")
    private Contact contact; // The contact involved in this history step (e.g. Endorsed To)

}
