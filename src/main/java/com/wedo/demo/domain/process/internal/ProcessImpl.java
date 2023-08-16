package com.wedo.demo.domain.process.internal;

import com.wedo.demo.domain.Context;
import com.wedo.demo.domain.process.Process;
import com.wedo.demo.domain.process.ProcessState;
import com.wedo.demo.domain.process.entity.ProcessInstanceEntity;

import java.util.HashMap;
import java.util.Map;

public class ProcessImpl implements Process {
    private final ProcessInstanceEntity entity;
    private final ProcessWorkUnit workUnit;

    public ProcessImpl(ProcessInstanceEntity entity, ProcessWorkUnit workUnit) {
        this.entity = entity;
        this.workUnit = workUnit;
    }


    @Override
    public Long getId() {
        return entity.getId();
    }

    @Override
    public String getKey() {
        return entity.getProcessKey();
    }

    @Override
    public String getProcessCode() {
        return entity.getProcessCode();
    }

    @Override
    public ProcessState getState() {
        return entity.getState();
    }

    @Override
    public Map<String, String> getFieldValues() {
        Map<String, String> fieldValues = new HashMap<>();

        this.entity.getFieldValues().forEach(fieldValue -> {
            fieldValues.put(fieldValue.getFieldName(), fieldValue.getFieldValue());
        });

        return fieldValues;
    }

    @Override
    public String submit(Context context) {
        return workUnit.submit(context, this.entity);
    }

    @Override
    public Long save(Context context) {
        return workUnit.save(context, this.entity);
    }

}
