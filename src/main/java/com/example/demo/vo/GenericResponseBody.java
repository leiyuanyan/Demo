package com.example.demo.vo;

import java.io.Serializable;

/**
 * @author leiyuanyan
 * @Description
 * @since 2021/3/30 15:38
 */
public class GenericResponseBody<T>  implements Serializable {
    int status;
    String message;
    T data;

    public GenericResponseBody() {
    }

    public GenericResponseBody(int status) {
        this.status = status;
    }

    public GenericResponseBody(int status, T data) {
        this.status = status;
        this.data = data;
    }

    public GenericResponseBody(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
