package com.dku.springstudy.controller;

import com.dku.springstudy.dto.common.SuccessResponse;
import com.dku.springstudy.dto.like.LikeClickResponseDto;
import com.dku.springstudy.security.CustomUserDetails;
import com.dku.springstudy.service.LikeService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "좋아요 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @Operation(
            summary = "좋아요 추가 / 취소",
            description = "좋아요를 누른 적이 있다면 좋아요를 취소하고, 누른 적이 없다면 좋아요를 추가한다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "회원 또는 상품 아이디(PK)가 존재하지 않는 경우"),
    })
    @PostMapping("/like/{productId}")
    public ResponseEntity<SuccessResponse<LikeClickResponseDto>> clickLikeBtn(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long productId
    ) {

        LikeClickResponseDto response = likeService.clickLikeBtn(customUserDetails.getId(), productId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new SuccessResponse<>(response));
    }
}
