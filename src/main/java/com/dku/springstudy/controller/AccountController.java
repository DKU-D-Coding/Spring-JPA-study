package com.dku.springstudy.controller;

import com.dku.springstudy.dto.LoginDTO;
import com.dku.springstudy.dto.LoginResponseDTO;
import com.dku.springstudy.jwt.JwtTokenProvider;
import com.dku.springstudy.model.Member;
import com.dku.springstudy.dto.SignupDTO;
import com.dku.springstudy.model.Role;
import com.dku.springstudy.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/account")
@RequiredArgsConstructor
public class AccountController {
    private final JwtTokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final MemberService memberService;

    @Value("${jwt.secret}")
    private String jwtSecretKey;
    private final long EXPIRED_MS = 30 * 60 * 1000L;

    @PostMapping("/signup")
    public String signup(SignupDTO memberForm) {
        String rawPassword = memberForm.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);

        Member newMember = new Member();
        newMember.setEmail(memberForm.getEmail());
        newMember.setPassword(encodedPassword);
        newMember.setNickname(memberForm.getNickname());
        newMember.setRole(Role.USER);
        memberService.join(newMember);

        return "success";
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginDTO loginInfo) {
        String loginEmail = loginInfo.getEmail();
        String loginRawPassword = loginInfo.getPassword();
        if (!memberService.login(loginEmail, loginRawPassword)) {
            throw new IllegalStateException("로그인에 실패했습니다.");
        }

        String accessToken = tokenProvider.createToken(loginEmail, jwtSecretKey, EXPIRED_MS);
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO(accessToken);

        return loginResponseDTO;
    }
}
