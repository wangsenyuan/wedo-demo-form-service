package com.wedo.demo.domain.process.entity;

import com.wedo.demo.domain.process.ProcessState;

import java.util.HashMap;
import java.util.Map;

public class ProcessInstanceEntity {
    private Long id;
    private String processCode;
    private String processKey;
    private ProcessState state;

    private String createdBy;

    private String updatedBy;

    private Map<String, String> formValues = new HashMap<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProcessCode() {
        return processCode;
    }

    public void setProcessCode(String processCode) {
        this.processCode = processCode;
    }

    public String getProcessKey() {
        return processKey;
    }

    public void setProcessKey(String processKey) {
        this.processKey = processKey;
    }

    public ProcessState getState() {
        return state;
    }

    public void setState(ProcessState state) {
        this.state = state;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Map<String, String> getFormValues() {
        return formValues;
    }

    public void setFormValues(Map<String, String> formValues) {
        this.formValues = formValues;
    }

    public void copyValue(ProcessInstanceEntity from) {
        if (from.getState() != null) {
            this.state = from.getState();
        }
        if (from.getProcessKey() != null) {
            this.processKey = from.getProcessKey();
        }
    }
}
