package com.wedo.demo.domain.attendance.impl;

import com.wedo.demo.domain.attendance.Attendance;
import com.wedo.demo.domain.attendance.entity.AttendanceEntity;

import java.util.Date;

public class AttendanceBuilder implements Attendance.Builder {
    private final AttendanceEntity entity;

    public AttendanceBuilder(AttendanceEntity entity) {
        this.entity = entity;
    }

    @Override
    public Attendance.Builder setOccurredAt(Date occurredAt) {
        this.entity.setOccurredAt(occurredAt);
        return this;
    }

    @Override
    public Attendance.Builder setCustomer(String customer) {
        this.entity.setCustomer(customer);
        return this;
    }

    @Override
    public Attendance.Builder setLocation(String location) {
        this.entity.setLocation(location);
        return this;
    }

    @Override
    public Attendance.Builder setImageUrl(String imageUrl) {
        this.entity.setImageUrl(imageUrl);
        return this;
    }

    @Override
    public Attendance.Builder setOperator(String operator) {
        this.entity.setOperator(operator);
        return this;
    }
}
