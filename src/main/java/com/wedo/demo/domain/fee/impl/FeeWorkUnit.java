package com.wedo.demo.domain.fee.impl;

import com.wedo.demo.domain.fee.FeeContext;
import com.wedo.demo.domain.fee.entity.FeeEntity;
import com.wedo.demo.domain.fee.repository.FeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeeWorkUnit {
    @Autowired
    private FeeRepository repository;

    public Long save(FeeEntity entity, FeeContext context) {
        entity.setUpdatedBy(context.getOperator());
        if (entity.getId() == null) {
            entity.setCreatedBy(context.getOperator());
        }
        repository.saveAndFlush(entity);
        return entity.getId();
    }

    public void updateProcess(FeeContext context, FeeEntity entity, String processKey) {
        entity.setProcessKey(processKey);
        entity.setUpdatedBy(context.getOperator());
        repository.updateProcessKey(entity.getId(), processKey);
    }
}
