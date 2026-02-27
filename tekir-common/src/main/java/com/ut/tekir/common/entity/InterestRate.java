package com.ut.tekir.common.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "INTEREST_RATE") @Getter @Setter @EqualsAndHashCode(of = "id", callSuper = false)
public class InterestRate extends AuditBase {
    private static final long serialVersionUID = 1L;
    @Id @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq") @Column(name = "ID") private Long id;
    @Column(name = "NAME", length = 100) @Size(max = 100) private String name;
    @Column(name = "ISACTIVE") private Boolean active = Boolean.TRUE;
    @OneToMany(mappedBy = "interestRate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InterestRateDetail> details = new ArrayList<>();
}
