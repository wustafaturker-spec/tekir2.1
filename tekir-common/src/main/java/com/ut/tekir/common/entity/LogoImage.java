package com.ut.tekir.common.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity @Table(name = "LOGO_IMAGE") @Getter @Setter @EqualsAndHashCode(of = "id", callSuper = false)
public class LogoImage extends AuditBase {
    private static final long serialVersionUID = 1L;
    @Id @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq") @Column(name = "ID") private Long id;
    @Column(name = "FILE_NAME", length = 100) private String fileName;
    @Lob @Column(name = "IMAGE_DATA") private byte[] imageData;
    @Column(name = "CONTENT_TYPE", length = 50) private String contentType;
}
