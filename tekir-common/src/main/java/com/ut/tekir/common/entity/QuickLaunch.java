package com.ut.tekir.common.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Quick launch shortcut entity.
 */
@Entity
@Table(name = "QUICK_LAUNCH")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class QuickLaunch extends AuditBase {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", length = 100)
    @Size(max = 100)
    private String name;

    @Column(name = "VIEW_ID", length = 250)
    @Size(max = 250)
    private String viewId;

    @Column(name = "ICON", length = 50)
    @Size(max = 50)
    private String icon;

    @Column(name = "ORDER_NO")
    private Integer orderNo;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
}
