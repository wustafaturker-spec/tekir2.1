package com.ut.tekir.common.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * POS Commission definition entity.
 */
@Entity
@Table(name = "POS_COMMISION")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class PosCommision extends AuditBase {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "POS_ID")
    private Pos pos;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PosCommisionDetail> details = new ArrayList<>();

    @Column(name = "ISACTIVE")
    private Boolean active = Boolean.TRUE;
}
