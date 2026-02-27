package com.ut.tekir.common.entity;

import com.ut.tekir.common.entity.AuditBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PAYMENT_PLAN")
@Getter
@Setter
public class PaymentPlan extends AuditBase {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @Column(name = "CODE", length = 20, nullable = false, unique = true)
    private String code;

    @Column(name = "NAME", length = 50)
    private String name;
    
    @Column(name = "INFO")
    private String info;
    
    @Column(name = "ISACTIVE")
    private Boolean active = Boolean.TRUE;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentPlanItem> items = new ArrayList<>();

}
