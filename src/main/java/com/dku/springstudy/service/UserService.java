package com.dku.springstudy.service;

import com.dku.springstudy.domain.User;
import com.dku.springstudy.dto.user.request.LoginRequest;
import com.dku.springstudy.dto.user.response.LoginResponse;
import com.dku.springstudy.dto.user.request.SignUpRequest;
import com.dku.springstudy.jwt.TokenProvider;
import com.dku.springstudy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Transactional
    public Long signUp(SignUpRequest signUpRequest) throws Exception {

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new Exception("이미 가입된 이메일입니다.");
        }

        User user = User.builder()
                .email(signUpRequest.getEmail())
                .name(signUpRequest.getName())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .phoneNumber(signUpRequest.getPhoneNumber())
                .nickname(signUpRequest.getNickname())
                .build();

        userRepository.save(user);

        return user.getId();
    }

    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일로 가입된 회원이 없습니다."));

        if(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            String loginAccessToken  = tokenProvider.createLoginAccessToken(user.getEmail(), user.getRole().name());
            String loginRefreshToken = tokenProvider.createLoginRefreshToken(user.getEmail());
            return LoginResponse.of(loginAccessToken, loginRefreshToken);
        } else {
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }
    }
}
