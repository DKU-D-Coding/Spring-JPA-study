package com.dku.springstudy.dto;

import lombok.Data;
/*
 요청 처리를 실패했을 때 돌려주는 DTO
 */
@Data
public class ErrorResponseResult {
    private final String message;
    private final boolean successful = false;

    public ErrorResponseResult(String message) {
        this.message = message;
    }

    public ErrorResponseResult(Exception e) {
        this(e.getMessage());
    }

}
