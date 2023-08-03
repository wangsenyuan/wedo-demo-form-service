package com.wedo.demo.domain.fee.service;

import com.wedo.demo.domain.fee.entity.FeeEntity;
import com.wedo.demo.domain.form.FormService;
import com.wedo.demo.domain.form.FormType;
import com.wedo.demo.domain.form.serializer.JsonSerializer;
import com.wedo.demo.domain.process.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class FeeService {
    @Autowired
    private FormService formService;
    @Autowired
    private ProcessService processService;

    final static String FEE_DEMO_PROCESS = "PROC-D5D5D54C-C651-4060-A56D-A76395A27FDC";

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

        return formService.updateForm(entity.getId(), entity, builder -> {
            builder.setOperator("system");
        });
    }


    public Long submit(FeeEntity fee) {
        Long id = save(fee);

        processService.submit(builder -> {
            builder.setId(id);
            builder.setProcessCode(FEE_DEMO_PROCESS);
            builder.addProperty("事由", fee.getReason());
            builder.addProperty("金额", fee.getAmount().toString());
            builder.addProperty("类型", fee.getType());
            builder.addProperty("出发地", fee.getDeparture());
            builder.addProperty("目的地", fee.getDestination());
        });

        return id;
    }
}
