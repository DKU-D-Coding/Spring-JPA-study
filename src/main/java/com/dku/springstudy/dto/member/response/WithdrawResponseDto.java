package com.dku.springstudy.dto.member.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class WithdrawResponseDto {
    private boolean success;
    private String message = "";

    public WithdrawResponseDto(boolean success){
        this.success = success;
    }

    public WithdrawResponseDto(boolean success, String message){
        this.success = success;
        this.message = message;
    }
}
