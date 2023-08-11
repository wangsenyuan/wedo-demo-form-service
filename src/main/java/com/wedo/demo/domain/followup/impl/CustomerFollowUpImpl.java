package com.wedo.demo.domain.followup.impl;

import com.wedo.demo.domain.followup.CustomerFollowUp;
import com.wedo.demo.domain.followup.CustomerFollowUpContext;
import com.wedo.demo.domain.followup.entity.CustomerFollowUpEntity;

import java.util.Date;

public class CustomerFollowUpImpl implements CustomerFollowUp {

    private final CustomerFollowUpEntity entity;
    private final CustomerFollowUpWorkUnit workUnit;

    public CustomerFollowUpImpl(CustomerFollowUpEntity entity, CustomerFollowUpWorkUnit workUnit) {
        this.entity = entity;
        this.workUnit = workUnit;
    }

    @Override
    public Long getId() {
        return this.entity.getId();
    }

    @Override
    public String getCustomer() {
        return this.entity.getCustomer();
    }

    @Override
    public String getOperator() {
        return this.entity.getOperator();
    }

    @Override
    public Date getOccurredAt() {
        return this.entity.getOccurredAt();
    }

    @Override
    public String getNote() {
        return this.entity.getNote();
    }

    @Override
    public String getAudioUrl() {
        return this.entity.getAudioUrl();
    }

    @Override
    public Long save(CustomerFollowUpContext context) {
        return workUnit.save(context, this.entity);
    }
}
