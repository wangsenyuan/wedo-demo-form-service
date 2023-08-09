package com.wedo.demo.dto.common;

import java.io.Serializable;

/**
 * service层返回对象列表封装
 *
 * @param <T>
 */
public class R<T> implements Serializable {

    private boolean success = false;

    private String code;

    private String message;

    private T data;

    private R() {
    }

    public static <T> R<T> success(T result) {
        R<T> item = new R<T>();
        item.success = true;
        item.data = result;
        item.code = "0";
        item.message = "success";
        return item;
    }

    public static <T> R<T> failure(String errorCode, String errorMessage) {
        R<T> item = new R<T>();
        item.success = false;
        item.code = errorCode;
        item.message = errorMessage;
        return item;
    }

    public static <T> R<T> failure(String errorCode) {
        R<T> item = new R<T>();
        item.success = false;
        item.code = errorCode;
        item.message = "failure";
        return item;
    }


    public boolean isSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
