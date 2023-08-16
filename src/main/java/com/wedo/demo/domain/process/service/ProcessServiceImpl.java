package com.wedo.demo.domain.process.service;

import com.wedo.demo.domain.Context;
import com.wedo.demo.domain.process.Process;
import com.wedo.demo.domain.process.ProcessService;
import com.wedo.demo.domain.process.entity.ProcessInstanceEntity;
import com.wedo.demo.domain.process.internal.ProcessBuilderImpl;
import com.wedo.demo.domain.process.internal.ProcessImpl;
import com.wedo.demo.domain.process.internal.ProcessWorkUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;
import java.util.function.Function;

@Service
public class ProcessServiceImpl implements ProcessService {
    @Autowired
    private ProcessWorkUnit workUnit;

    @Override
    @Transactional
    public <T> T submit(Context context, String processCode, Consumer<Process.Builder> updater, Function<Process, T> fn) {
        ProcessInstanceEntity entity = new ProcessInstanceEntity();
        entity.setProcessCode(processCode);
        Process.Builder builder = new ProcessBuilderImpl(entity);
        updater.accept(builder);

        Process process = new ProcessImpl(entity, workUnit);
        process.save(context);
        process.submit(context);

        return fn.apply(process);
    }
}
