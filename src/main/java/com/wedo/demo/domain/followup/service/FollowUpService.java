package com.wedo.demo.domain.followup.service;

import com.wedo.demo.domain.followup.entity.CustomerFollowUpEntity;
import com.wedo.demo.domain.form.FormService;
import com.wedo.demo.domain.form.FormType;
import com.wedo.demo.domain.form.serializer.JsonSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;

@Service
public class FollowUpService {

    @Autowired
    private FormService formService;

    @PostConstruct
    public void init() {
        formService.registerSerializer(FormType.FOLLOW_UP, new JsonSerializer<>(CustomerFollowUpEntity.class));
    }

    public Long save(CustomerFollowUpEntity entity) {
        entity.setOccurredAt(new Date());
        if (entity.getId() == null) {
            return formService.createForm(FormType.ATTENDANCE, entity, builder -> {
                builder.setFormVersion("v1");
                builder.setOperator("system");
            });
        }

        return formService.updateForm(entity.getId(), entity, builder -> {
            builder.setOperator("system");
        });
    }
}
