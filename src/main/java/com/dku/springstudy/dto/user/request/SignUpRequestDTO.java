package com.dku.springstudy.dto.user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SignUpRequestDTO {

    @Email(message = "Email format does not match")
    @NotBlank(message = "Please input email")
    private String email;

    @NotBlank(message = "Please input password")
    private String password;

    @NotBlank(message = "Please input name")
    private String name;

}
