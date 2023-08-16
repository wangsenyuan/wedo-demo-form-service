package com.wedo.demo.domain.process.entity;

import com.wedo.demo.domain.process.ProcessState;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "process_instance")
@Entity
public class ProcessInstanceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String processCode;
    private String processKey;
    @Enumerated(EnumType.STRING)
    private ProcessState state;

    private String createdBy;

    private String updatedBy;

    @ElementCollection(targetClass = ProcessInstanceFieldValue.class)
    @JoinTable(name = "process_instance_field_value")
    @JoinColumn(name = "process_instance_id", referencedColumnName = "id", columnDefinition = "bigint")
    private List<ProcessInstanceFieldValue> fieldValues;

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

    public List<ProcessInstanceFieldValue> getFieldValues() {
        return fieldValues;
    }

    public void setFieldValues(List<ProcessInstanceFieldValue> fieldValues) {
        this.fieldValues = fieldValues;
    }

    public void addFieldValue(String field, String value) {
        if (this.fieldValues == null) {
            this.fieldValues = new ArrayList<>();
        }
        ProcessInstanceFieldValue fieldValue = new ProcessInstanceFieldValue();
        fieldValue.setFieldName(field);
        fieldValue.setFieldValue(value);
        this.fieldValues.add(fieldValue);
    }
}
