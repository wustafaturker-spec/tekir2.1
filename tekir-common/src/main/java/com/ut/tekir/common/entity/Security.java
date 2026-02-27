package com.ut.tekir.common.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Security (investment instrument) entity.
 */
@Entity
@Table(name = "SECURITY")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class Security extends AuditBase {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PORTFOLIO_ID")
    private Portfolio portfolio;

    @Column(name = "NAME", length = 100)
    private String name;

    @Column(name = "ISIN", length = 20)
    private String isin;

    @Column(name = "ISACTIVE")
    private Boolean active = Boolean.TRUE;

    @OneToMany(mappedBy = "security", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SecurityCoupon> coupons = new ArrayList<>();
}
