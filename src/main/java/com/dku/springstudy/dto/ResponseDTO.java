package com.dku.springstudy.dto;

import lombok.Data;

@Data
public class ResponseDTO<T> {
    private int status;
    private T data;
}
