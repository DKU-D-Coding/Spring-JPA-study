package com.dku.springstudy.service;

import com.dku.springstudy.domain.User;
import com.dku.springstudy.dto.user.request.LoginRequest;
import com.dku.springstudy.dto.user.response.LoginResponse;
import com.dku.springstudy.dto.user.request.SignUpRequest;
import com.dku.springstudy.exception.CustomException;
import com.dku.springstudy.exception.ErrorCode;
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
    public Long signUp(SignUpRequest signUpRequest) {

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new CustomException(ErrorCode.USER_EMAIL_Duplication);
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
                .orElseThrow(() -> new CustomException(ErrorCode.USER_EMAIL_NOT_FOUND));

        if(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            String loginAccessToken  = tokenProvider.createLoginAccessToken(user.getEmail(), user.getRole().name());
            String loginRefreshToken = tokenProvider.createLoginRefreshToken(user.getEmail());
            return LoginResponse.of(loginAccessToken, loginRefreshToken);
        } else {
            throw new CustomException(ErrorCode.USER_PASSWORD_NOT_MATCHES);
        }
    }
}
