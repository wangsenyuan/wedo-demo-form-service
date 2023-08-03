package com.wedo.demo.domain.process;

public interface Process {
    Long getId();

    String getProcessCode();

    String getKey();

    ProcessState getState();

    String submit();
}
