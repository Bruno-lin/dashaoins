package com.dsmh.common.response;

import org.springframework.http.ResponseEntity;

/**
 * @author TeamScorpio
 * @since 2022/3/1
 */
public class WebResult {

    public static <T> ResponseEntity<WebData<T>> ok(T data) {
        WebData<T> webData = new WebData<>(data);
        return ResponseEntity.ok(webData);
    }

    public static ResponseEntity<?> noContent() {

        return ResponseEntity.ok(new WebData<>());
    }

}
