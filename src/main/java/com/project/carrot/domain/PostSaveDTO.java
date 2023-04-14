package com.project.carrot.domain;

import lombok.*;

import java.util.Collections;

@Data
public class PostSaveDTO {
    private String EMAIL;
    private String PASS;
    private String TEL;
    private String USERNAME;
    private String NICKNAME;
    private String ADDRESS;
    //private String

    //final KafkaProperties.Admin ADMIN = Admin.일반회원; 권한설정 필요할 경우 설정


    @Builder
    public PostSaveDTO(String EMAIL,String PASS,String TEL,String USERNAME,String NICKNAME,String ADDRESS){
        this.EMAIL=EMAIL;
        this.PASS=PASS;
        this.TEL=TEL;
        this.USERNAME=USERNAME;
        this.NICKNAME=NICKNAME;
        this.ADDRESS=ADDRESS;
    }

    public Member toEntity(){
        return Member.builder()
                .UserEmail(EMAIL)
                .UserPass(PASS)
                .UserTel(TEL)
                .UserName(USERNAME)
                .NickName(NICKNAME)
                .Address(ADDRESS)
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
    }
}


