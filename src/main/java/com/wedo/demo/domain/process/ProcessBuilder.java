package com.wedo.demo.domain.process;

import com.wedo.demo.domain.process.internal.ProcessWorkUnit;

public interface ProcessBuilder {

    ProcessBuilder setId(Long id);

    ProcessBuilder setProcessCode(String processCode);

    ProcessBuilder addProperty(String field, String value);

    Process build(ProcessWorkUnit workUnit);

}
