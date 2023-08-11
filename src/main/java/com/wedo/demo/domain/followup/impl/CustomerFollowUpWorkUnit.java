package com.wedo.demo.domain.followup.impl;

import com.wedo.demo.domain.followup.CustomerFollowUpContext;
import com.wedo.demo.domain.followup.entity.CustomerFollowUpEntity;
import com.wedo.demo.domain.followup.repository.CustomerFollowUpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerFollowUpWorkUnit {
    @Autowired
    private CustomerFollowUpRepository repository;

    public Long save(CustomerFollowUpContext context, CustomerFollowUpEntity entity) {
        entity.setUpdatedBy(context.getOperator());

        if (entity.getId() == null) {
            entity.setCreatedBy(context.getOperator());
        }

        entity = repository.saveAndFlush(entity);

        return entity.getId();
    }
}
