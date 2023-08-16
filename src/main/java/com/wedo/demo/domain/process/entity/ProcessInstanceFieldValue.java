package com.wedo.demo.domain.process.entity;


import com.google.common.base.MoreObjects;

import javax.persistence.Embeddable;

@Embeddable
public class ProcessInstanceFieldValue {

    private String fieldName;

    private String fieldValue;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("fieldName", fieldName).add("fieldValue", fieldValue).toString();
    }
}
