package com.dku.springstudy.domain.user.controller;


import com.dku.springstudy.domain.user.dto.LoginRequestDto;
import com.dku.springstudy.domain.user.service.SecurityUserService;
import com.dku.springstudy.jwt.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {
    private final SecurityUserService securityUserService;

    @PostMapping("/login")
    public TokenDto login(@RequestBody LoginRequestDto loginRequestDto) {
        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();
        TokenDto tokenDto = securityUserService.login(email, password);
        return tokenDto;
    }
}
