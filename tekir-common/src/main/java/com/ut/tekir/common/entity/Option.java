package com.ut.tekir.common.entity;

import com.ut.tekir.common.entity.AuditBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "T_OPTION") // "OPTION" might be reserved keyword in some DBs
@Getter
@Setter
public class Option extends AuditBase {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @Column(name = "OPT_KEY", unique = true, length = 100)
    private String key;

    @Column(name = "OPT_VALUE", length = 500)
    private String value;

    @Column(name = "USER_SPECIFIC")
    private Boolean userSpecific = Boolean.FALSE;
    
    @Column(name = "FOR_USER")
    private String forUser; // If user specific
}
