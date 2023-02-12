package com.dku.springstudy.dto;

import com.dku.springstudy.enums.ItemStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class UpdateItemStatusDto {
    @ApiModelProperty(value = "수정할 상품 id", dataType = "Long")
    private Long id;
    @ApiModelProperty(value = "수정할 상품 상태", dataType = "string")
    private ItemStatus itemStatus;
}
