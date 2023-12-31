package com.wedo.demo.dto.followup;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wedo.demo.domain.followup.CustomerFollowUp;

import java.util.Date;

public class CustomerFollowUpDto {

    private Long id;

    private String customer;

    private String operator;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date occurredAt;

    private String note;

    private String audioUrl;

    private String formType = "FOLLOW_UP";

    public CustomerFollowUpDto() {

    }

    public CustomerFollowUpDto(CustomerFollowUp domain) {
        this.id = domain.getId();
        this.customer = domain.getCustomer();
        this.occurredAt = domain.getOccurredAt();
        this.note = domain.getNote();
        this.audioUrl = domain.getAudioUrl();
        this.operator = domain.getOperator();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getOccurredAt() {
        return occurredAt;
    }

    public void setOccurredAt(Date occurredAt) {
        this.occurredAt = occurredAt;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }
}
