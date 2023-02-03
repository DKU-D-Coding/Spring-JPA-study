package com.cha.carrotApi.service;

import com.cha.carrotApi.domain.User;
import com.cha.carrotApi.jwt_security.JwtTokenProvider;
import com.cha.carrotApi.repository.UserRepository;
import com.cha.carrotApi.DTO.SignUpRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public Long signUp(SignUpRequestDto requestDto) throws Exception {

        if(userRepository.findByEmail(requestDto.getEmail()).isPresent()){
            throw new Exception("이미 존재하는 이메일입니다.");
        }

        User user = userRepository.save(requestDto.toEntity());
        user.encodePassword(passwordEncoder);

        user.addUserAuthority();
        return user.getId();
    }


    // 위에랑 마찬가지로 변경
    public String login(Map<String, String> users) {
        User user = userRepository.findByEmail(users.get("email"))
                .orElseThrow(() -> new IllegalStateException("가입되지 않은 Email 입니다."));
        String password = users.get("password");
        if (!passwordEncoder.matches(password,user.getPassword())) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }

        List<String> roles = new ArrayList<>();
        roles.add(user.getRole().name());

        return jwtTokenProvider.createToken(user.getEmail(), roles);
    }
}
