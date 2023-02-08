package com.dku.springstudy.dto;

import com.dku.springstudy.enums.ItemStatus;
import lombok.Getter;

@Getter
public class UpdateItemStatusDto {
    private Long id;
    private ItemStatus itemStatus;
}
