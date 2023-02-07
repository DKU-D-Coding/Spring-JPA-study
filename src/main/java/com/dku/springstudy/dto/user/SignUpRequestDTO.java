package com.dku.springstudy.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class SignUpRequestDTO {

    @Email
    @NotBlank
    private final String email;

    @NotBlank
    @Length(min = 8, max = 50)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private final String password;
    private final String username;
    @NotBlank
    @Pattern(regexp = "^[0-9-]{3,20}$")
    private final String phoneNumber;
    private final String nickname;
}
