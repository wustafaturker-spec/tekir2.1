package com.ut.tekir.common.embeddable;

import com.ut.tekir.common.entity.Contact;
import com.ut.tekir.common.enums.WorkStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Embeddable
@Getter
@Setter
public class WorkBunch {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WORK_CONTACT_ID")
    private Contact contact;

    @Column(name = "WORK_CAPTION")
    private String caption;
    
    @Column(name = "WORK_NOTE")
    private String note;
    
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "WORK_STATUS")
    private WorkStatus status;

    @Column(name = "WORK_BEGIN_DATE")
    private LocalDateTime beginDate;

    @Column(name = "WORK_END_DATE")
    private LocalDateTime endDate;
}
