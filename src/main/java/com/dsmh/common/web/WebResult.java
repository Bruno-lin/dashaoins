package com.dsmh.common.web;

/**
 * @author TeamScorpio
 * @since 2022/3/10
 */
public class WebResult<T> extends BaseResult{

    private final T data;


    public WebResult(T data) {
        super();
        this.data = data;
    }

    public WebResult() {
        this.data = null;
    }

    public T getData() {
        return data;
    }

    public static <T> WebResult<T> ok(T data) {
        return new WebResult<>(data);
    }

    public static <T> WebResult<T> noContent() {
        return new WebResult<>();
    }
}
