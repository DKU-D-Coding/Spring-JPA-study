package com.cha.carrotApi.controller;

import com.cha.carrotApi.DTO.user.SignUpRequest;
import com.cha.carrotApi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //회원가입
    @PostMapping("/join")
    @ResponseStatus(HttpStatus.OK)
    public Long join(@Valid @RequestBody SignUpRequest request) throws Exception {
        return userService.signUp(request);
    }

    //로그인
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> user) {
        return userService.login(user);
    }
}
