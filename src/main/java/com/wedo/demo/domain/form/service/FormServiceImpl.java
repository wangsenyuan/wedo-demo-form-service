package com.wedo.demo.domain.form.service;

import com.wedo.demo.domain.form.*;
import com.wedo.demo.domain.form.internal.FormContextImpl;
import com.wedo.demo.domain.form.internal.FormWorkUnit;
import com.wedo.demo.domain.form.repository.FormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;

@Service
public class FormServiceImpl implements FormService {
    private FormRepository formRepository;

    private FormWorkUnit workUnit;

    public FormServiceImpl() {
    }


    @Autowired
    public void setFormRepository(FormRepository formRepository) {
        this.formRepository = formRepository;
    }

    @Autowired
    public void setWorkUnit(FormWorkUnit workUnit) {
        this.workUnit = workUnit;
    }

    @Override
    public <T> void registerSerializer(FormType formType, FormSerializer<T> serializer) {
        this.workUnit.registerSerializer(formType, serializer);
    }

    private FormContext getFormContext() {
        return new FormContextImpl();
    }

    @Override
    @Transactional
    public <T> Long createForm(FormType formType, T formContent, Consumer<FormBuilderImpl<T>> consumer) {
        String content = workUnit.serializeForm(formType, formContent);

        FormBuilderImpl<T> builder = new FormBuilderImpl<>(formType, content, workUnit);
        consumer.accept(builder);

        Form<T> form = builder.build(getFormContext());

        return form.save();
    }


}
