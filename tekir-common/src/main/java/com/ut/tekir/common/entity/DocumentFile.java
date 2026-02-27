package com.ut.tekir.common.entity;

import com.ut.tekir.common.enums.DocumentFileType;
import com.ut.tekir.common.enums.DocumentType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Document file attachment entity.
 */
@Entity
@Table(name = "DOCUMENT_FILE")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class DocumentFile extends AuditBase {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", length = 250)
    @Size(max = 250)
    private String name;

    @Column(name = "MIME_TYPE", length = 100)
    @Size(max = 100)
    private String mimeType;

    @Column(name = "FILE_TYPE")
    @Enumerated(EnumType.ORDINAL)
    private DocumentFileType fileType;

    @Column(name = "DOCUMENT_TYPE")
    @Enumerated(EnumType.ORDINAL)
    private DocumentType documentType;

    @Column(name = "DOCUMENT_ID")
    private Long documentId;

    @Lob
    @Column(name = "CONTENT")
    private byte[] content;
}
