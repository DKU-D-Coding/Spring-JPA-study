package com.dku.springstudy.controller;

import com.dku.springstudy.dto.common.SuccessResponse;
import com.dku.springstudy.dto.user.response.UserUpdateResponseDto;
import com.dku.springstudy.security.CustomUserDetails;
import com.dku.springstudy.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Api(tags = "사용자 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "회원 프로필 수정",
            description = "사용자로부터 닉네임과 사진을 입력받아 회원의 정보를 수정한다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "아이디(PK)가 존재하지 않는 경우"),
    })
    @PatchMapping("/user/profile")
    public ResponseEntity<SuccessResponse<UserUpdateResponseDto>> update(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @Valid @RequestPart(value = "nickname") String nickname,
            @RequestPart(value = "profile", required = false) MultipartFile file
    ) {
        UserUpdateResponseDto response = userService.update(customUserDetails.getId(), nickname, file);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new SuccessResponse<>(response));
    }

    @Operation(
            summary = "회원 프로필 사진 삭제",
            description = "로그인된 사용자의 프로필 사진을 삭제한다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "아이디(PK)가 존재하지 않는 경우"),
    })
    @DeleteMapping("/user/profile")
    public ResponseEntity<SuccessResponse<UserUpdateResponseDto>> deleteImg(@AuthenticationPrincipal CustomUserDetails customUserDetails) {

        UserUpdateResponseDto response = userService.deleteImg(customUserDetails.getId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new SuccessResponse<>(response));
    }
}
