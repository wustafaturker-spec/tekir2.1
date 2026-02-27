package com.ut.tekir.common.entity;

import java.time.LocalDate;

/**
 * Document Interface used for Document definitions like Invoice, Payment etc.
 */
public interface Document {
    Boolean getActive();
    String getCode();
    LocalDate getDate();
    String getInfo();
    String getReference();
    String getSerial();

    void setActive(Boolean active);
    void setCode(String code);
    void setDate(LocalDate date);
    void setInfo(String info);
    void setReference(String reference);
    void setSerial(String serial);
}
