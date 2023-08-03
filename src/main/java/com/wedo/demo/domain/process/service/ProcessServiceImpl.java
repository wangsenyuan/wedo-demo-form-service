package com.wedo.demo.domain.process.service;

import com.wedo.demo.domain.process.Process;
import com.wedo.demo.domain.process.ProcessBuilder;
import com.wedo.demo.domain.process.ProcessService;
import com.wedo.demo.domain.process.internal.ProcessBuilderImpl;
import com.wedo.demo.domain.process.internal.ProcessWorkUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;

@Service
public class ProcessServiceImpl implements ProcessService {
    @Autowired
    private ProcessWorkUnit workUnit;

    @Override
    @Transactional
    public String submit(Consumer<ProcessBuilder> taskBuilder) {
        ProcessBuilder builder = new ProcessBuilderImpl();

        taskBuilder.accept(builder);

        Process process = builder.build(workUnit);

        return process.submit();
    }
}
