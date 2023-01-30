package com.dku.springstudy.dto.common;

import lombok.Getter;

@Getter
public class ErrorResponse extends BaseResponse {

    private int errorCode;
    private String errorMessage;

    public ErrorResponse(int errorCode, String errorMessage) {
        super(false);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
