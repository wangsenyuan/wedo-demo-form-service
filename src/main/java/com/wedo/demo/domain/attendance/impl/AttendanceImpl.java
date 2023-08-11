package com.wedo.demo.domain.attendance.impl;

import com.wedo.demo.domain.attendance.Attendance;
import com.wedo.demo.domain.attendance.AttendanceContext;
import com.wedo.demo.domain.attendance.entity.AttendanceEntity;

import java.util.Date;

public class AttendanceImpl implements Attendance {
    private final AttendanceEntity entity;
    private final AttendanceWorkUnit workUnit;

    public AttendanceImpl(AttendanceEntity entity, AttendanceWorkUnit workUnit) {
        this.entity = entity;
        this.workUnit = workUnit;
    }

    @Override
    public Long getId() {
        return this.entity.getId();
    }

    @Override
    public Date getOccurredAt() {
        return this.entity.getOccurredAt();
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
    public String getLocation() {
        return this.entity.getLocation();
    }

    @Override
    public String getImageUrl() {
        return this.entity.getImageUrl();
    }

    @Override
    public Long save(AttendanceContext context) {
        return workUnit.save(entity, context);
    }
}
