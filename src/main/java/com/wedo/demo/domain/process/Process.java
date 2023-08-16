package com.wedo.demo.domain.process;

import com.wedo.demo.domain.Context;

import java.util.Map;

public interface Process {
    Long getId();

    String getProcessCode();

    String getKey();

    ProcessState getState();

    Map<String, String> getFieldValues();

    String submit(Context context);

    Long save(Context context);

    interface Builder {
        Builder addField(String field, String value);
    }
}
