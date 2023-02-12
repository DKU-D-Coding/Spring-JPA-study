package com.dku.springstudy.controller;

import com.dku.springstudy.service.ItemLikeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/item-like")
public class ItemLikeController {

    private final ItemLikeService itemLikeService;

    @ApiOperation(value = "상품 좋아요 추가 API", notes = "상품 좋아요 기능")
    @ApiResponses({
            @ApiResponse(code = 200, message = "API 정상 작동")
    }
    )
    @PostMapping("/add/{itemId}")
    public void addItemLike(@PathVariable Long itemId){
        itemLikeService.addItemLike(itemId);
    }


    @ApiOperation(value = "상품 좋아요 삭제 API", notes = "상품 좋아요 취소 기능")
    @ApiResponses({
            @ApiResponse(code = 200, message = "API 정상 작동")
    }
    )
    @PostMapping("/delete/{itemId}")
    public void deleteItemLike(@PathVariable Long itemId){
        itemLikeService.deleteItemLike(itemId);
    }
}
