package com.ut.tekir.common.entity;

import com.ut.tekir.common.entity.AuditBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "INTEREST")
@Getter
@Setter
public class Interest extends AuditBase {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "CODE", length = 20)
    private String code;

    @Column(name = "RATE", precision = 10, scale = 4)
    private BigDecimal rate;
    
    @Column(name = "INFO")
    private String info;
}
