package com.dku.springstudy.dto.user.request;

// import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LogoutRequestDTO {
    private String token;
}
