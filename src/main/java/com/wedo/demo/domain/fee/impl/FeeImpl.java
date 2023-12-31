package com.wedo.demo.domain.fee.impl;

import com.wedo.demo.domain.fee.Fee;
import com.wedo.demo.domain.fee.FeeContext;
import com.wedo.demo.domain.fee.entity.FeeEntity;

import java.math.BigDecimal;
import java.util.Date;

public class FeeImpl implements Fee {
    private final FeeEntity entity;
    private final FeeWorkUnit workUnit;

    public FeeImpl(FeeEntity entity, FeeWorkUnit workUnit) {
        this.entity = entity;
        this.workUnit = workUnit;
    }

    @Override
    public Long getId() {
        return this.entity.getId();
    }

    @Override
    public String getReason() {
        return this.entity.getReason();
    }

    @Override
    public String getType() {
        return this.entity.getType();
    }

    @Override
    public String getTypeName() {
        return this.entity.getTypeName();
    }

    @Override
    public BigDecimal getAmount() {
        return this.entity.getAmount();
    }

    @Override
    public Date getOccurredAt() {
        return this.entity.getOccurredAt();
    }

    @Override
    public String getDeparture() {
        return this.entity.getDeparture();
    }

    @Override
    public String getDestination() {
        return this.entity.getDestination();
    }

    @Override
    public String getLocation() {
        return this.entity.getLocation();
    }

    @Override
    public String getProcessKey() {
        return this.entity.getProcessKey();
    }

    @Override
    public Long save(FeeContext context) {
        return workUnit.save(entity, context);
    }

    @Override
    public void updateProcess(FeeContext context, String processKey) {
        this.workUnit.updateProcess(context, this.entity, processKey);
    }
}
