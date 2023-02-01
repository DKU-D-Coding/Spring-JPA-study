package com.dku.springstudy.controller;

import com.dku.springstudy.dto.common.SuccessResponse;
import com.dku.springstudy.dto.user.request.LoginRequestDto;
import com.dku.springstudy.dto.user.response.LoginResponseDto;
import com.dku.springstudy.dto.user.request.SignUpRequestDto;
import com.dku.springstudy.dto.user.response.SignUpResponseDto;
import com.dku.springstudy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<SuccessResponse<SignUpResponseDto>> signUp(@Valid @RequestBody SignUpRequestDto signUpRequestDto) {
        SignUpResponseDto userId = userService.signUp(signUpRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new SuccessResponse<>(userId));
    }

    @PostMapping("/login")
    public ResponseEntity<SuccessResponse<LoginResponseDto>> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto tokens = userService.login(loginRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new SuccessResponse<>(tokens));
    }
}
