package com.wedo.demo.domain.attendance.impl;

import com.wedo.demo.domain.attendance.AttendanceContext;
import com.wedo.demo.domain.attendance.entity.AttendanceEntity;
import com.wedo.demo.domain.attendance.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttendanceWorkUnit {

    @Autowired
    private AttendanceRepository repository;

    public Long save(AttendanceEntity entity, AttendanceContext context) {
        entity.setUpdatedBy(context.getOperator());

        if (entity.getId() == null) {
            entity.setCreatedBy(context.getOperator());
        }

        entity = repository.saveAndFlush(entity);

        return entity.getId();
    }
}
