package com.dsmh.common.advice;

import com.dsmh.common.trouble.Trouble;
import com.dsmh.common.web.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author TeamScorpio
 * @since 2022/3/1
 */
@Slf4j
@RestControllerAdvice
public class CommonControllerAdvice {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResult methodNotValid(MethodArgumentNotValidException e) {
        return new BaseResult(e);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public BaseResult httpMessageNotReadable(HttpMessageNotReadableException e) {
        return new BaseResult(e);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public BaseResult missingServletRequestParameter(MissingServletRequestParameterException e) {
        return new BaseResult(e);
    }

    @ExceptionHandler(Trouble.class)
    public BaseResult trouble(Trouble trouble) {
        return new BaseResult(trouble);
    }

    @ExceptionHandler(Exception.class)
    public BaseResult unknownException(Exception e) {
        log.error("System error", e);
        return new BaseResult(e);
    }

}
