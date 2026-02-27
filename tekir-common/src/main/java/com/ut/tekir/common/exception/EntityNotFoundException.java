package com.ut.tekir.common.exception;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException {

    private final String entityName;
    private final Object entityId;

    public EntityNotFoundException(String entityName, Object entityId) {
        super(entityName + " not found with id: " + entityId);
        this.entityName = entityName;
        this.entityId = entityId;
    }
}
