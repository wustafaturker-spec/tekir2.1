package com.ut.tekir.common.entity;

import com.ut.tekir.common.entity.AuditBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "BANK")
@Getter
@Setter
public class Bank extends AuditBase {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @Column(name = "CODE", length = 20, nullable = false, unique = true)
    private String code;

    @Column(name = "NAME", length = 50)
    private String name;

    @Column(name = "SWIFT_CODE", length = 20)
    private String swiftCode;
    
    @Column(name = "ISACTIVE")
    private Boolean active = Boolean.TRUE;

}
