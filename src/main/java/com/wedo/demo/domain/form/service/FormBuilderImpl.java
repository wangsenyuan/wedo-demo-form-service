package com.wedo.demo.domain.form.service;

import com.wedo.demo.domain.form.Form;
import com.wedo.demo.domain.form.FormBuilder;
import com.wedo.demo.domain.form.FormContext;
import com.wedo.demo.domain.form.FormType;
import com.wedo.demo.domain.form.entity.FormEntity;
import com.wedo.demo.domain.form.internal.FormImpl;
import com.wedo.demo.domain.form.internal.FormWorkUnit;

public class FormBuilderImpl<T> implements FormBuilder<T> {
    private FormEntity entity;
    private FormWorkUnit workUnit;

    FormBuilderImpl(FormType formType, String content, FormWorkUnit workUnit) {
        this.entity = new FormEntity();
        this.entity.setFormType(formType);
        this.entity.setFormContent(content);
        this.workUnit = workUnit;
    }

    public FormBuilderImpl<T> setId(Long id) {
        this.entity.setId(id);
        return this;
    }


    public FormBuilderImpl<T> setFormVersion(String formVersion) {
        this.entity.setFormVersion(formVersion);
        return this;
    }

    public FormBuilderImpl<T> setOperator(String operator) {
        this.entity.setCreatedBy(operator);
        this.entity.setUpdatedBy(operator);
        return this;
    }


    @Override
    public Form<T> build(FormContext formContext) {
        return new FormImpl<>(entity, workUnit);
    }

}
