package com.dku.springstudy.auth;

import lombok.Getter;

@Getter
public class SecretKey {

    private final String jwtSecretKey;
    private final Long jwtValidityTime;
    private final Long refreshValidityTime;

    public SecretKey(String jwtSecretKey, Long jwtValidityTime, Long refreshValidityTime) {
        this.jwtSecretKey = jwtSecretKey;
        this.jwtValidityTime = jwtValidityTime;
        this.refreshValidityTime = refreshValidityTime;
    }
}
