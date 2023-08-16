package com.wedo.demo.domain.process.entity;


import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class ProcessInstanceFieldValue {

    @ManyToOne
    @JoinColumn(name = "process_instance_id")
    private ProcessInstanceEntity processInstance;

    private String fieldName;

    private String fieldValue;

    public ProcessInstanceEntity getProcessInstance() {
        return processInstance;
    }

    public void setProcessInstance(ProcessInstanceEntity processInstance) {
        this.processInstance = processInstance;
    }

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
}
