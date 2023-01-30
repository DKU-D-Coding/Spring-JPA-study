package com.dku.springstudy.controller;

import com.dku.springstudy.dto.common.SuccessResponse;
import com.dku.springstudy.dto.user.request.LoginRequest;
import com.dku.springstudy.dto.user.response.LoginResponse;
import com.dku.springstudy.dto.user.request.SignUpRequest;
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
    public ResponseEntity<SuccessResponse<Long>> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        Long userId = userService.signUp(signUpRequest);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new SuccessResponse<Long>(userId));
    }

    @PostMapping("/login")
    public ResponseEntity<SuccessResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse response = userService.login(loginRequest);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new SuccessResponse<LoginResponse>(response));
    }
}
