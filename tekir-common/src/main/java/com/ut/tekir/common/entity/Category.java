package com.ut.tekir.common.entity;

import java.util.List;

/**
 * Category interface for hierarchical entities (product categories, contact categories, etc.)
 */
public interface Category {
    Long getId();
    Category getParent();
    List<?> getChildList();

    void setPath(String path);
    String getPath();
    String getCode();
    Integer getWeight();
    Boolean getActive();
}
