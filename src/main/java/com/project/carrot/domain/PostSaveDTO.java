package com.project.carrot.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostSaveDTO {
    private String EMAIL;
    private String PASS;
    private String TEL;
    private String USERNAME;
    private String NICKNAME;
    private String ADDRESS;

    //final KafkaProperties.Admin ADMIN = Admin.일반회원; 권한설정 필요할 경우 설정
}


