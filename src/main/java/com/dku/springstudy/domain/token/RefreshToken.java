package com.dku.springstudy.domain.token;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "refreshToken", timeToLive = 3600)
@Getter
public class RefreshToken {

    @Id
    private String memberEmail;
    private String refreshToken;

    public RefreshToken(String memberEmail, String refreshToken){
        this.memberEmail = memberEmail;
        this.refreshToken = refreshToken;
    }



}
