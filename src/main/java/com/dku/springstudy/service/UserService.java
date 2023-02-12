package com.dku.springstudy.service;

import com.dku.springstudy.domain.User;
import com.dku.springstudy.dto.user.response.UserUpdateResponseDto;
import com.dku.springstudy.exception.CustomException;
import com.dku.springstudy.exception.ErrorCode;
import com.dku.springstudy.s3.service.S3Service;
import com.dku.springstudy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    private final UserRepository userRepository;

    private final S3Service s3Service;

    @Transactional
    public UserUpdateResponseDto update(Long loginMemberId, String nickname, MultipartFile file) {

        User user = userRepository.findById(loginMemberId).orElseThrow(() -> new CustomException(ErrorCode.USER_ID_NOT_FOUND));
        String imgUrl = null;

        if(!file.isEmpty()) {
            imgUrl = s3Service.uploadFile(file);
        }

        user.update(nickname, imgUrl);

        return UserUpdateResponseDto.of(nickname, imgUrl);
    }

    @Transactional
    public UserUpdateResponseDto deleteImg(Long loginMemberId) {

        User user = userRepository.findById(loginMemberId).orElseThrow(() -> new CustomException(ErrorCode.USER_ID_NOT_FOUND));
        s3Service.deleteFile(user.getImgUrl());
        user.deleteImg();

        return UserUpdateResponseDto.of(user.getNickname(), null);
    }
}
