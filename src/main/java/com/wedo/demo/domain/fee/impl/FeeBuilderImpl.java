package com.wedo.demo.domain.fee.impl;

import com.wedo.demo.domain.fee.Fee;
import com.wedo.demo.domain.fee.entity.FeeEntity;

import java.math.BigDecimal;
import java.util.Date;

public class FeeBuilderImpl implements Fee.Builder {

    private final FeeEntity entity;

    public FeeBuilderImpl(FeeEntity entity) {
        this.entity = entity;
    }

    @Override
    public Fee.Builder setReason(String reason) {
        this.entity.setReason(reason);
        return this;
    }

    @Override
    public Fee.Builder setType(String type) {
        this.entity.setType(type);
        return this;
    }

    @Override
    public Fee.Builder setAmount(BigDecimal amount) {
        this.entity.setAmount(amount);
        return this;
    }

    @Override
    public Fee.Builder setOccurredAt(Date occurredAt) {
        this.entity.setOccurredAt(occurredAt);
        return this;
    }

    @Override
    public Fee.Builder setDeparture(String departure) {
        this.entity.setDeparture(departure);
        return this;
    }

    @Override
    public Fee.Builder setDestination(String destination) {
        this.entity.setDestination(destination);
        return this;
    }

    @Override
    public Fee.Builder setLocation(String location) {
        this.entity.setLocation(location);
        return this;
    }

    @Override
    public Fee.Builder setTypeName(String typeName) {
        this.entity.setTypeName(typeName);
        return this;
    }
}
