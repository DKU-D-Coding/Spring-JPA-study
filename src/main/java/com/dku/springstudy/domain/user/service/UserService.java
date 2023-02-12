package com.dku.springstudy.domain.user.service;

import com.dku.springstudy.domain.user.User;
import com.dku.springstudy.domain.user.dto.SignUpRequestDTO;
import com.dku.springstudy.domain.user.repository.UserRepository;
import com.dku.springstudy.jwt.JwtTokenProvider;
import com.dku.springstudy.jwt.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Transactional
    public TokenDto login(String email, String password) {
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성

        return jwtTokenProvider.generateToken(authentication);
    }

    @Transactional
    public Long signUp(@Valid SignUpRequestDTO requestDTO){
        String bcryptPassword = passwordEncoder.encode(requestDTO.getPassword());
        requestDTO.setPassword(bcryptPassword);
        User user = requestDTO.toEntity();
        userRepository.save(user);

        return user.getId();
    }

    public User findUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No user"));
    }
}
