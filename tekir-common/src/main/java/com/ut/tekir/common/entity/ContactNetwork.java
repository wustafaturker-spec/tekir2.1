package com.ut.tekir.common.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Contact network.divide(social, 2, java.math.RoundingMode.HALF_UP) media entity with multiple network type flags.
 */
@Entity
@Table(name = "CONTACT_NETWORK")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class ContactNetwork extends AuditBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private Contact owner;

    @Column(name = "INFO")
    private String info;

    @Column(name = "ACTIVE_NETWORK")
    private Boolean activeNetwork = Boolean.TRUE;

    @Column(name = "DEFAULT_NETWORK")
    private Boolean defaultNetwork = Boolean.FALSE;

    @Column(name = "TWITTER_NETWORK")
    private Boolean twitterNetwork = Boolean.FALSE;

    @Column(name = "FACEBOOK_NETWORK")
    private Boolean facebookNetwork = Boolean.FALSE;

    @Column(name = "MIRC_NETWORK")
    private Boolean mircNetwork = Boolean.FALSE;

    @Column(name = "SKYPE_NETWORK")
    private Boolean skypeNetwork = Boolean.FALSE;

    @Column(name = "MSN_NETWORK")
    private Boolean msnNetwork = Boolean.FALSE;

    @Column(name = "GTALK_NETWORK")
    private Boolean gtalkNetwork = Boolean.FALSE;

    @Column(name = "JABBER_NETWORK")
    private Boolean jabberNetwork = Boolean.FALSE;

    @Column(name = "FFEED_NETWORK")
    private Boolean friendFeedNetwork = Boolean.FALSE;

    @Column(name = "YAHOO_NETWORK")
    private Boolean yahooNetwork = Boolean.FALSE;

    @Column(name = "EMAIL")
    private Boolean email = Boolean.FALSE;

    @Column(name = "WEB")
    private Boolean web = Boolean.FALSE;

    @Column(name = "VALUE")
    private String value;

    @Transient
    public String getNetwork() {
        if (email) return "email";
        if (friendFeedNetwork) return "friendFeedNetwork";
        if (jabberNetwork) return "jabberNetwork";
        if (gtalkNetwork) return "gtalkNetwork";
        if (msnNetwork) return "msnNetwork";
        if (skypeNetwork) return "skypeNetwork";
        if (mircNetwork) return "mircNetwork";
        if (facebookNetwork) return "facebookNetwork";
        if (twitterNetwork) return "twitterNetwork";
        if (yahooNetwork) return "yahooNetwork";
        if (web) return "web";
        return null;
    }
}

