package com.wedo.demo.domain.form.internal;

import com.wedo.demo.domain.form.FormSerializer;
import com.wedo.demo.domain.form.FormService;
import com.wedo.demo.domain.form.FormType;
import com.wedo.demo.domain.form.entity.FormEntity;
import com.wedo.demo.domain.form.repository.FormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class FormWorkUnit {
    private ConcurrentMap<FormType, FormSerializer<?>> serializers = new ConcurrentHashMap<>();

    private FormRepository formRepository;

    private FormService formService;

    @Autowired
    public void setFormRepository(FormRepository formRepository) {
        this.formRepository = formRepository;
    }

    public <T> void registerSerializer(FormType formType, FormSerializer<T> serializer) {
        this.serializers.put(formType, serializer);
    }

    public <T> String serializeForm(FormType formType, T formContent) {
        return ((FormSerializer<T>) this.serializers.get(formType)).serialize(formContent);
    }

    public <T> T deserializeForm(FormType formType, String formContent) {
        return ((FormSerializer<T>) this.serializers.get(formType)).deserialize(formContent);
    }

    public Long save(FormEntity entity) {
        return this.formRepository.save(entity);
    }
}
