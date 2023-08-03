package com.wedo.demo.domain.process.internal;

import com.wedo.demo.domain.process.Process;
import com.wedo.demo.domain.process.ProcessBuilder;
import com.wedo.demo.domain.process.entity.ProcessInstanceEntity;

import java.util.HashMap;
import java.util.Map;

public class ProcessBuilderImpl implements ProcessBuilder {
    private ProcessInstanceEntity entity = new ProcessInstanceEntity();
    private Map<String, String> values = new HashMap<>();

    @Override
    public ProcessBuilder setId(Long id) {
        entity.setId(id);
        return this;
    }

    @Override
    public ProcessBuilder setProcessCode(String processCode) {
        entity.setProcessCode(processCode);
        return this;
    }

    @Override
    public ProcessBuilder addProperty(String field, String value) {
        values.put(field, value);
        return this;
    }

    @Override
    public Process build(ProcessWorkUnit workUnit) {
        entity.setFormValues(values);
        return new ProcessImpl(entity, workUnit);
    }
}
