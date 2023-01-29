package com.dku.springstudy.controller;

import com.dku.springstudy.dto.user.request.LoginRequest;
import com.dku.springstudy.dto.user.response.LoginResponse;
import com.dku.springstudy.dto.user.request.SignUpRequest;
import com.dku.springstudy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<Long> signUp(@Valid @RequestBody SignUpRequest signUpRequest) throws Exception {
        Long userId = userService.signUp(signUpRequest);
        return ResponseEntity.ok(userId);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse response = userService.login(loginRequest);
        return ResponseEntity.ok(response);
    }
}
