package com.dku.springstudy.service;

import com.dku.springstudy.config.security.jwt.JwtProvider;
import com.dku.springstudy.dto.*;
import com.dku.springstudy.model.Role;
import com.dku.springstudy.model.User;
import com.dku.springstudy.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signUp(SignUpRequestDTO signUpRequest){

        boolean isExist = userRepository.existsByEmail(signUpRequest.getEmail());
        if(isExist){
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(signUpRequest.getPassword());
        User user = User.builder()
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .password(encodedPassword)
                .phoneNumber(signUpRequest.getPhoneNumber())
                .profileImageUrl("https://user-images.githubusercontent.com/79920930/215020145-7d548f6e-a45e-4041-913c-24a16081a07a.jpg")
                .nickname(signUpRequest.getNickname())
                .role(Role.USER)
                .build();

        userRepository.save(user);
    }

    @Transactional
    public UserResponseDTO login(LoginRequestDTO loginRequest) throws JsonProcessingException {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new IllegalStateException("아이디 혹은 비밀번호를 확인하세요."));

        boolean matches = passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());
        if(!matches){
            throw new IllegalStateException("아이디 혹은 비밀번호를 확인하세요.");
        }

        UserResponseDTO userResponse = UserResponseDTO.of(user);
        final String token = jwtProvider.createTokensByLogin(userResponse).getAtk();
        user.setToken(token);

        return UserResponseDTO.of(user);
    }

    @Transactional
    public UserInformationChangeResponseDTO changeImageAndNickname(@AuthenticationPrincipal String userId, UserInformationChangeRequestDTO userImageChangeDTO){
        User user = userRepository.findById(userId).orElseThrow(()-> new IllegalStateException("오류 : 없는 사용자 입니다."));
        String newImage = userImageChangeDTO.getImageUrl();
        String newNickname = userImageChangeDTO.getNickname();

        user.changeProfileImageAndNickname(newImage,newNickname);

        UserInformationChangeResponseDTO userResponse = UserInformationChangeResponseDTO.builder().
                imageUrl(userImageChangeDTO.getImageUrl())
                .nickname(userImageChangeDTO.getNickname())
                .build();

        return userResponse;
    }
}
