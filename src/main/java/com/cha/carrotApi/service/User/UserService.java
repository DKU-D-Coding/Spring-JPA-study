package com.cha.carrotApi.service.User;

import com.cha.carrotApi.domain.User.User;
import com.cha.carrotApi.exception.CustomException;
import com.cha.carrotApi.exception.ErrorCode;
import com.cha.carrotApi.jwt_security.JwtTokenProvider;
import com.cha.carrotApi.repository.User.UserRepository;
import com.cha.carrotApi.DTO.user.SignUpRequest;
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
    public Long signUp(SignUpRequest requestDto) throws Exception {

        checkUser(requestDto);

        User user = userRepository.save(requestDto.toEntity());
        user.encodePassword(passwordEncoder);

        user.addUserAuthority();
        return user.getId();
    }

    @Transactional
    public String login(Map<String, String> users) {
        User user = userRepository.findByEmail(users.get("email"))
                .orElseThrow(() -> new CustomException(ErrorCode.USER_EMAIL_NOT_FOUND));
        String password = users.get("password");
        if (!passwordEncoder.matches(password,user.getPassword())) {
            throw new CustomException(ErrorCode.USER_PASSWORD_INVALID);
        }

        List<String> roles = new ArrayList<>();
        roles.add(user.getRole().name());

        return jwtTokenProvider.createToken(user.getEmail(), roles);
    }

    public boolean checkUser (SignUpRequest requestDto) {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new CustomException(ErrorCode.USER_EMAIL_DUPLICATED);
        }
        if (userRepository.findByNickname(requestDto.getNickname()).isPresent()){
            throw new CustomException(ErrorCode.USER_NICKNAME_DUPLICATED);
        }
        if (userRepository.findByPhonenumber(requestDto.getPhonenumber()).isPresent()) {
            throw new CustomException(ErrorCode.USER_PHONE_NUMBER_DUPLICATED);
        }
        return true;
    }
}
