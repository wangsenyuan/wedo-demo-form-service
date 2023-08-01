package com.wedo.demo.domain.form.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wedo.demo.domain.form.FormSerializer;
import org.apache.commons.lang3.StringUtils;

public class JsonSerializer<T> implements FormSerializer<T> {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Class<T> clazz;

    public JsonSerializer(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public String serialize(T content) {
        try {
            return objectMapper.writeValueAsString(content);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T deserialize(String content) {
        if (StringUtils.isEmpty(content)) {
            return null;
        }
        try {
            return objectMapper.readValue(content, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
