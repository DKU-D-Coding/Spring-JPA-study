package com.dku.springstudy.domain.dto;

import lombok.Data;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;

@Data
public class ResponseEntity<T> extends HttpEntity<T> {
    private HttpStatus status;
    private T body;

    public ResponseEntity(@Nullable T body, HttpStatus status) {
//        super(body, headers);
        this.body = body;
        this.status = status;
    }
}
