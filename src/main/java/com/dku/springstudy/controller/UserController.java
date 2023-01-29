package com.dku.springstudy.controller;

import com.dku.springstudy.config.security.jwt.JwtProvider;
import com.dku.springstudy.dto.*;
import com.dku.springstudy.model.User;
import com.dku.springstudy.repository.UserRepository;
import com.dku.springstudy.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final JwtProvider jwtProvider;

    @PostMapping("/account/sign-up")
    @ApiOperation(value = "회원가입", notes = "SignUpRequest 객체로 받아 유저를 등록합니다.")
    public ResponseDTO<?> signUp(@RequestBody SignUpRequest signUpRequest){
        userService.signUp(signUpRequest);
        return new ResponseDTO<>(HttpStatus.OK.value(), signUpRequest);
    }

    @PostMapping("/account/login")
    @ApiOperation(value = "로그인", notes = "LoginRequest 객체로 email과 password를 받아 로그인을 진행하고, access token과 refresh token을 리턴합니다.")
    public ResponseDTO<?> login(@RequestBody LoginRequest loginRequest) throws JsonProcessingException {
        UserResponse userResponse = userService.login(loginRequest);
        TokenResponse tokens = jwtProvider.createTokensByLogin(userResponse);
        return new ResponseDTO<>(HttpStatus.OK.value(), tokens);
    }

    @GetMapping("/account/reissue")
    @ApiOperation(value = "access토큰 재발급", notes = "access 토큰이 만료된 경우 refresh 토큰을 이용하여 access 토큰을 갱신합니다.")
    public ResponseDTO<?> reissue(@AuthenticationPrincipal String userId) throws JsonProcessingException {
        User user = userRepository.findById(userId).orElseThrow(()->new IllegalStateException("오류 : 유저없음"));
        UserResponse userResponse = UserResponse.of(user);
        TokenResponse tokens = jwtProvider.reissueAtk(userResponse);
        return new ResponseDTO<>(HttpStatus.OK.value(),tokens);
    }
}
