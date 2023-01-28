package com.project.carrot.domain;

import lombok.*;
import org.apache.catalina.User;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@Entity //객체라는 것을 알림
@ToString
@Builder
public class Member {
    @Id //Id는 pk
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long UserId;
    private String UserEmail;
    private String UserPass;
    private String UserTel;
    private String UserName;
    private String NickName;
    private String Address;
    @Builder.Default private double MannerTemp=36.5;
    @Builder.Default
    private String ProfilePhotoURL=null;

}

