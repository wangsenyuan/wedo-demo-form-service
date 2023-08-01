package com.wedo.demo.domain.form.internal;

import com.wedo.demo.domain.form.Form;
import com.wedo.demo.domain.form.FormType;
import com.wedo.demo.domain.form.entity.FormEntity;

public class FormImpl<T> implements Form<T> {
    private final FormEntity entity;
    private final FormWorkUnit workUnit;

    public FormImpl(FormEntity entity, FormWorkUnit workUnit) {
        this.entity = entity;
        this.workUnit = workUnit;
    }

    @Override
    public Long getId() {
        return entity.getId();
    }

    @Override
    public FormType getFormType() {
        return entity.getFormType();
    }

    @Override
    public String getFormVersion() {
        return entity.getFormVersion();
    }

    @Override
    public T getFormContent() {
        return workUnit.deserializeForm(entity.getFormType(), entity.getFormContent());
    }

    @Override
    public Long save() {
        return workUnit.save(this.entity);
    }
}
