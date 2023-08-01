package com.wedo.demo.domain.fee.service;

import com.wedo.demo.domain.fee.entity.FeeEntity;
import com.wedo.demo.domain.form.FormService;
import com.wedo.demo.domain.form.FormType;
import com.wedo.demo.domain.form.serializer.JsonSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class FeeService {
    @Autowired
    private FormService formService;

    @PostConstruct
    public void init() {
        formService.registerSerializer(FormType.FEE, new JsonSerializer<>(FeeEntity.class));
    }

    public Long save(FeeEntity entity) {
        // TODO need do validation
        if (entity.getId() == null) {
            return formService.createForm(FormType.FEE, entity, builder -> {
                builder.setFormVersion("v1");
                builder.setOperator("system");
            });
        }

        return entity.getId();
    }

}
