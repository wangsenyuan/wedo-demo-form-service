package com.wedo.demo.domain.form;

import java.util.Map;

public interface Form<T> {
    Long getId();

    FormType getFormType();

    String getFormVersion();

    T getFormContent();

    Long save();
}
