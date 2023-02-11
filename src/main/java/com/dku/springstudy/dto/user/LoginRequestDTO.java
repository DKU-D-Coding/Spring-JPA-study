package com.dku.springstudy.dto.user;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class LoginRequestDTO {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Length(min = 8, max = 20)
    private String password;
}
