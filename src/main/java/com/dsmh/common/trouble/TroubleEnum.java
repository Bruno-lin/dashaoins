package com.dsmh.common.trouble;

import org.springframework.http.HttpStatus;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * @author TeamScorpio
 * @since 2021/01/01
 */
public interface TroubleEnum {

    default Trouble causeBy(Throwable cause) {
        Trouble trouble = TroubleHolder.get(this);
        Trouble newTrouble = new Trouble(trouble.getHttpStatus(), trouble.getCode(), trouble.getMessage());
        newTrouble.reason(trouble.getReason());
        newTrouble.initCause(cause);
        return newTrouble;
    }

    default TroubleEnum reason(String reason) {
        Trouble trouble = TroubleHolder.get(this);
        trouble.reason(reason);
        return this;
    }

    default void thrown() {
        TroubleHolder.get(this).thrown();
    }

    default Trouble getTrouble() {
        return TroubleHolder.get(this);
    }

    default String getCode() {
        return getTrouble().getCode();
    }

    default String getMessage() {
        return getTrouble().getMessage();
    }

    default HttpStatus getHttpStatus() {
        final HttpStatus status = getTrouble().getHttpStatus();
        if (status == null) {
            throw new IllegalStateException("This enumeration doesn't support HTTP status, please use @HttpTrouble to support it.");
        }
        return status;
    }

    default Throwable getCause() {
        return getTrouble().getCause();
    }

    default void printStackTrace() {
        getTrouble().printStackTrace();
    }

    default void printStackTrace(PrintStream s) {
        getTrouble().printStackTrace(s);
    }

    default void printStackTrace(PrintWriter s) {
        getTrouble().printStackTrace(s);
    }



}
