package com.connormcwood.studentrest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ChildResourceNotFoundException extends RuntimeException {
    private String resourceName;
    private String fieldName;
    private Object fieldValue;
    private String parentName;

    public ChildResourceNotFoundException( String resourceName, String fieldName, Object fieldValue, String parentName ) {
        super(String.format("%s not found within parent %s. Invalid %s : '%s'", resourceName, parentName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        this.parentName = parentName;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() { return fieldName; }

    public Object getFieldValue() {
        return fieldValue;
    }

    public String getParentName() { return parentName; }

}
