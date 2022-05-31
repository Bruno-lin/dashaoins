package com.dsmh.common.response;

import com.dsmh.common.trouble.Trouble;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;

/**
 * @author TeamScorpio
 * @since 2022/3/1
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class WebData<T> {


    private int code = 0;

    private String message = "请求成功";

    private String reason;

    private T data;

    private long timestamp = System.currentTimeMillis();

    public WebData() {
    }

    public WebData(T data) {
        this.data = data;
    }

    public WebData(Trouble trouble) {
        this.code = Integer.parseInt(trouble.getCode());
        this.message = trouble.getMessage();
        this.reason = trouble.getReason();
    }

    public WebData(HttpMessageNotReadableException e) {
        this.code = -2;
        this.message = "请求失败";
        this.reason = e.getMessage();
    }

    public WebData(MissingServletRequestParameterException e) {
        this.code = -2;
        this.message = "请求失败";
        this.reason = e.getMessage();
    }

    public WebData(MethodArgumentNotValidException e) {
        this.code = -2;
        this.message = "请求失败";
        FieldError fieldError = e.getBindingResult().getFieldError();
        if (fieldError != null) {
            this.reason = String.format("%s: %s", fieldError.getField(), fieldError.getDefaultMessage());
        }
    }





}
