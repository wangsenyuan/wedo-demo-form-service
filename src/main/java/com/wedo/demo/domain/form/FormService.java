package com.wedo.demo.domain.form;

import com.wedo.demo.domain.form.service.FormBuilderImpl;

import java.util.function.Consumer;

public interface FormService {

    <T> void registerSerializer(FormType formType, FormSerializer<T> serializer);

    <T> Long createForm(FormType formType, T formContent, Consumer<FormBuilderImpl<T>> builder);

    <T> Long updateForm(Long id, T formContent, Consumer<FormBuilderImpl<T>> consumer);
}
