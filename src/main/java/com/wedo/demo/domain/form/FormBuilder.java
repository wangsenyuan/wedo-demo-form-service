package com.wedo.demo.domain.form;

public interface FormBuilder<T> {
    FormBuilder<T> setId(Long id);

    FormBuilder<T> setFormVersion(String formVersion);

    FormBuilder<T> setOperator(String operator);

    Form<T> build(FormContext context);
}
