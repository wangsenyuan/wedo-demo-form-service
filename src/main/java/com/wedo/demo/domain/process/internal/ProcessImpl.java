package com.wedo.demo.domain.process.internal;

import com.wedo.demo.domain.process.Process;
import com.wedo.demo.domain.process.ProcessState;
import com.wedo.demo.domain.process.entity.ProcessInstanceEntity;

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
    public String submit() {
        return workUnit.submit(entity);
    }
}
