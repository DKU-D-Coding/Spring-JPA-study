package com.dku.springstudy.controller;


import java.security.Timestamp;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dku.springstudy.dto.user.request.LoginRequestDTO;
import com.dku.springstudy.dto.user.request.SignUpRequestDTO;
import com.dku.springstudy.dto.user.response.LoginResponseDTO;
import com.dku.springstudy.dto.user.response.LogoutResponseDTO;
import com.dku.springstudy.dto.user.response.SignUpResponseDTO;
import com.dku.springstudy.model.User;
import com.dku.springstudy.security.CustomUserDetails;
import com.dku.springstudy.security.jwt.JwtProvider;
import com.dku.springstudy.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

// why not commit

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtProvider jwtProvider;

    // private Long id;
    // private String name;
    // private String email;
    // private String password;
    

    // final Long ID = 30L;
    // final String NAME = "kangho";
    // final String EMAIL = "kangho@gmail.com";
    // final String PASSWORD = "kangho";
    
    // User user = User.builder()
    //         .id(ID)
    //         .name(NAME)
    //         .email(EMAIL)
    //         .password(PASSWORD)
    //         .build();

    @PostMapping("/user/signUpTest")
    public SignUpResponseDTO SignUpTest(@Valid @RequestBody SignUpRequestDTO signUpRequestDTO) {
        return userService.signUp(signUpRequestDTO);
    }

    @PostMapping("/user/login")
    public LoginResponseDTO login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        return userService.login(loginRequest);
    }

    @PostMapping("/user/logout")
    public LogoutResponseDTO logout(@AuthenticationPrincipal CustomUserDetails customUserDetails, HttpServletRequest request) {
        User user = customUserDetails.getUser();
        String accessToken = jwtProvider.resolveToken(request);
        return userService.logout(user, accessToken);
    }
}