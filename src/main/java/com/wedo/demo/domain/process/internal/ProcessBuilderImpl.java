package com.wedo.demo.domain.process.internal;

import com.wedo.demo.domain.process.Process;
import com.wedo.demo.domain.process.entity.ProcessInstanceEntity;

public class ProcessBuilderImpl implements Process.Builder {
    private final ProcessInstanceEntity entity;

    public ProcessBuilderImpl(ProcessInstanceEntity entity) {
        this.entity = entity;
    }

    @Override
    public Process.Builder addField(String field, String value) {
        this.entity.addFieldValue(field, value);
        return this;
    }
}
