package com.dku.springstudy.service;


import com.dku.springstudy.dto.user.request.LoginRequestDTO;
import com.dku.springstudy.dto.user.request.SignUpRequestDTO;
import com.dku.springstudy.dto.user.response.LoginResponseDTO;
import com.dku.springstudy.dto.user.response.LogoutResponseDTO;
import com.dku.springstudy.dto.user.response.SignUpResponseDTO;
import com.dku.springstudy.exception.CustomException;
import com.dku.springstudy.exception.ErrorCode;
import com.dku.springstudy.model.User;
import com.dku.springstudy.repository.UserRepository;
import com.dku.springstudy.security.jwt.JwtProvider;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RedisTemplate<String,String> redisTemplate;

    public SignUpResponseDTO signUp(SignUpRequestDTO membershipRequestDTO){
        User user = User.builder()
                .email(membershipRequestDTO.getEmail())
                .name(membershipRequestDTO.getName())
                .password(passwordEncoder.encode(membershipRequestDTO.getPassword()))
                .build();

        userRepository.save(user);

        String loginAccessToken  = jwtProvider.createAccessToken(user.getName());
        String loginRefreshToken = jwtProvider.createRefreshToken(user.getName());

        return new SignUpResponseDTO(loginAccessToken, loginRefreshToken);
    }
    
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO){

        User user = userRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND_ERROR));

        if(passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
            String accessToken  = jwtProvider.createAccessToken(user.getName());
            String refreshToken = jwtProvider.createRefreshToken(user.getName());
            redisTemplate.opsForValue()
                    .set(user.getEmail(), refreshToken,
                            jwtProvider.getExpiration(refreshToken), TimeUnit.MILLISECONDS);

            return new LoginResponseDTO(accessToken, refreshToken);
        } else {
            throw new CustomException(ErrorCode.USER_PASSWORD_INCORRECT_ERROR);
        }

    }

    public LogoutResponseDTO logout(User user, String accessToken){
        try {
            Long expiration = jwtProvider.getExpiration(accessToken);
            redisTemplate.opsForValue().set(accessToken, "logout", expiration, TimeUnit.MILLISECONDS);
            redisTemplate.delete(user.getEmail());

            return new LogoutResponseDTO(true);
        }catch (Exception e){
            return  new LogoutResponseDTO(false, e.getMessage());
        }
    }
}
