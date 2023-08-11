package com.wedo.demo.domain.followup.entity;

import com.google.common.base.MoreObjects;

import java.util.Date;

public class CustomerFollowUpEntity {
    private Long id;

    private String customer;

    private String operator;

    private Date occurredAt;

    private String note;

    private String audioUrl;

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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).add("customer", customer).add("operator", operator).add("occurredAt", occurredAt).add("note", note).add("audioUrl", audioUrl).toString();
    }
}
