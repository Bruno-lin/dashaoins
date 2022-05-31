package com.dsmh.common.trouble;

import org.springframework.http.HttpStatus;

/**
 * @author TeamScorpio
 * @since 2021/01/01
 */
public class Trouble extends RuntimeException implements Cloneable {

    private final String code;

    private final HttpStatus httpStatus;

    private String reason;

    public Trouble(HttpStatus httpStatus, String code, String message) {
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
    }


    public String getCode() {
        return code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }


    public void thrown() {
        throw this;
    }

    public Trouble reason(String reason) {
        this.reason = reason;
        return this;
    }

    public String getReason() {
        return reason;
    }
}
