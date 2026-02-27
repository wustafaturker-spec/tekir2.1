package com.ut.tekir.common.entity;

import java.io.Serializable;

import com.ut.tekir.common.enums.EInvoiceProvider;
import com.ut.tekir.common.enums.UblGenerationMode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Tenant-level e-Invoice integration settings.
 * Stores provider credentials and feature flags.
 */
@Entity
@Table(name = "EINVOICE_SETTINGS")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class EInvoiceSettings extends AuditBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "PROVIDER_TYPE", length = 30, nullable = false)
    private EInvoiceProvider providerType = EInvoiceProvider.FORIBA;

    @Column(name = "API_URL", length = 500)
    @Size(max = 500)
    private String apiUrl;

    @Column(name = "API_KEY", length = 500)
    @Size(max = 500)
    private String apiKey;

    @Column(name = "API_SECRET", length = 500)
    @Size(max = 500)
    private String apiSecret;

    @Column(name = "USERNAME", length = 100)
    @Size(max = 100)
    private String username;

    @Column(name = "PASSWORD", length = 500)
    @Size(max = 500)
    private String password;

    @Column(name = "SENDER_VKN", length = 11)
    @Size(max = 11)
    private String senderVkn;

    @Column(name = "SENDER_ALIAS", length = 255)
    @Size(max = 255)
    private String senderAlias;

    @Column(name = "IS_ACTIVE")
    private Boolean active = Boolean.FALSE;

    @Column(name = "EINVOICE_ENABLED")
    private Boolean einvoiceEnabled = Boolean.FALSE;

    @Column(name = "EARCHIVE_ENABLED")
    private Boolean earchiveEnabled = Boolean.FALSE;

    @Column(name = "EDESPATCH_ENABLED")
    private Boolean edespatchEnabled = Boolean.FALSE;

    @Column(name = "EPRODUCER_ENABLED")
    private Boolean eproducerEnabled = Boolean.FALSE;

    @Column(name = "ESMM_ENABLED")
    private Boolean esmmEnabled = Boolean.FALSE;

    @Enumerated(EnumType.STRING)
    @Column(name = "UBL_GENERATION_MODE", length = 20)
    private UblGenerationMode ublGenerationMode = UblGenerationMode.PROVIDER_SIDE;

    @Column(name = "DEFAULT_TEMPLATE", length = 100)
    @Size(max = 100)
    private String defaultTemplate;
}
