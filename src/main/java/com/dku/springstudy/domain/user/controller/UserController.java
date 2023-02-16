package com.dku.springstudy.domain.user.controller;


import com.dku.springstudy.domain.user.User;
import com.dku.springstudy.domain.user.dto.LoginRequestDto;
import com.dku.springstudy.domain.user.dto.SignUpRequestDTO;
import com.dku.springstudy.domain.user.dto.UserProfileUpdateRequestDTO;
import com.dku.springstudy.domain.user.service.UserService;
import com.dku.springstudy.jwt.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody SignUpRequestDTO requestDTO) {
        userService.signUp(requestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @PostMapping("/login")
    public TokenDto login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();
        return userService.login(email, password);
    }

    @PostMapping("/profile")
    public ResponseEntity<?> updateProfile(@AuthenticationPrincipal UserDetails userDetails,
                                           @Valid @RequestBody UserProfileUpdateRequestDTO requestDTO) {
        User loginUser = userService.findUser(userDetails.getUsername());
        Long id = userService.updateProfile(loginUser, requestDTO);

        return ResponseEntity.status(HttpStatus.OK).body(id);
    }
}
