package com.wedo.demo.domain.followup.impl;

import com.wedo.demo.domain.followup.CustomerFollowUp;
import com.wedo.demo.domain.followup.entity.CustomerFollowUpEntity;

public class CustomerFollowUpBuilder implements CustomerFollowUp.Builder {

    private final CustomerFollowUpEntity entity;

    public CustomerFollowUpBuilder(CustomerFollowUpEntity entity) {
        this.entity = entity;
    }

    @Override
    public CustomerFollowUp.Builder customer(String customer) {
        this.entity.setCustomer(customer);
        return this;
    }

    @Override
    public CustomerFollowUp.Builder operator(String operator) {
        this.entity.setOperator(operator);
        return this;
    }

    @Override
    public CustomerFollowUp.Builder note(String note) {
        this.entity.setNote(note);
        return this;
    }

    @Override
    public CustomerFollowUp.Builder audioUrl(String audioUrl) {
        this.entity.setAudioUrl(audioUrl);
        return this;
    }
}
