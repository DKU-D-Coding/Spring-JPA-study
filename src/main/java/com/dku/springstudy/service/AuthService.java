package com.dku.springstudy.service;

import com.dku.springstudy.domain.User;
import com.dku.springstudy.dto.auth.request.LoginRequestDto;
import com.dku.springstudy.dto.auth.request.SignUpRequestDto;
import com.dku.springstudy.dto.auth.response.LoginResponseDto;
import com.dku.springstudy.dto.auth.response.SignUpResponseDto;
import com.dku.springstudy.exception.CustomException;
import com.dku.springstudy.exception.ErrorCode;
import com.dku.springstudy.repository.UserRepository;
import com.dku.springstudy.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto) {

        if (userRepository.existsByEmail(signUpRequestDto.getEmail())) {
            throw new CustomException(ErrorCode.USER_EMAIL_Duplication);
        }

        User user = User.builder()
                .email(signUpRequestDto.getEmail())
                .name(signUpRequestDto.getName())
                .password(passwordEncoder.encode(signUpRequestDto.getPassword()))
                .phoneNumber(signUpRequestDto.getPhoneNumber())
                .nickname(signUpRequestDto.getNickname())
                .build();

        Long id = userRepository.save(user).getId();

        return SignUpResponseDto.of(id);
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_EMAIL_NOT_FOUND));

        if(passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            String loginAccessToken  = jwtTokenProvider.createLoginAccessToken(user.getEmail(), user.getRole().name());
            String loginRefreshToken = jwtTokenProvider.createLoginRefreshToken(user.getEmail());
            return LoginResponseDto.of(loginAccessToken, loginRefreshToken);
        } else {
            throw new CustomException(ErrorCode.USER_PASSWORD_NOT_MATCHES);
        }
    }
}
