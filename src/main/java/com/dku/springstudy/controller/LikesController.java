package com.dku.springstudy.controller;

import com.dku.springstudy.dto.ResponseDTO;
import com.dku.springstudy.service.LikesService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikesController {

    private final LikesService likesService;

    @ApiOperation(value = "상품 좋아요 기능", notes = "좋아요 기능 이미 했으면 취소 안했으면 추가를 실행한다")
    @GetMapping("/likes/{itemId}")
    public ResponseDTO<?> clickLikes(@AuthenticationPrincipal String userId, @PathVariable Long itemId) {
        return new ResponseDTO<>(HttpStatus.OK.value(), likesService.clickLikes(userId, itemId));
    }

    @ApiOperation(value = "나의 관심 목록", notes = "내가 좋아요 한 물품들의 목록을 보여줍니다.")
    @GetMapping("/likes/love")
    public ResponseDTO<?> myLikeItems(@AuthenticationPrincipal String userId){
        return new ResponseDTO<>(HttpStatus.OK.value(), likesService.likeItems(userId));
    }
}
