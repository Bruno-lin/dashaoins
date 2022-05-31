package com.dsmh.common.web;

import com.dsmh.common.trouble.Trouble;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;

/**
 * @author TeamScorpio
 * @since 2022/3/10
 */
public class BaseResult {

    private String code = "0";

    private String message = "请求成功";

    private String reason;

    private final long timestamp = System.currentTimeMillis();



    public BaseResult(Trouble trouble) {
        this.code = trouble.getCode();
        this.message = trouble.getMessage();
        this.reason = trouble.getReason();
    }

    public BaseResult(Exception e) {
        this.code = "-1";
        this.message = "系统错误";
        this.reason = e.getClass().getSimpleName() + ":" + e.getMessage();
    }

    public BaseResult(HttpMessageNotReadableException e) {
        this.code = "-2";
        this.message = "请求失败";
        this.reason = e.getMessage();
    }

    public BaseResult(MissingServletRequestParameterException e) {
        this.code = "-2";
        this.message = "请求失败";
        this.reason = e.getMessage();
    }

    public BaseResult(MethodArgumentNotValidException e) {
        this.code = "-2";
        this.message = "请求失败";
        FieldError fieldError = e.getBindingResult().getFieldError();
        if (fieldError != null) {
            this.reason = String.format("%s: %s", fieldError.getField(), fieldError.getDefaultMessage());
        }
    }

    public BaseResult() {

    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getReason() {
        return reason;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
