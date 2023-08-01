package com.wedo.demo.domain.form;

public interface FormSerializer<T> {

    String serialize(T content);

    T deserialize(String content);

}
