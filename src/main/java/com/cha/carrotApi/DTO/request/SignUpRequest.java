package com.cha.carrotApi.DTO.request;

import com.cha.carrotApi.domain.User;
import com.cha.carrotApi.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

@Data
@Getter
@Builder
@AllArgsConstructor
public class SignUpRequest {

    protected SignUpRequest(){}
    @Email(message = "이메일 형식에 맞지 않습니다.")
    @NotBlank(message = "아이디를 입력해주세요.")
    private String email;

    @NotBlank(message = "닉네임을 입력해주세요")
    @Size(min=2, message = "닉네임이 너무 짧습니다.")
    private String nickname;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,30}$",
            message = "비밀번호는 8~30 자리이면서 1개 이상의 알파벳, 숫자, 특수문자를 포함해야합니다.")
    private String password;

    @NotBlank(message = "핸드폰 번호를 입력해주세요.")
    @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$",
            message = "핸드폰 번호는 010-xxxx-xxxx 형식으로 입력해주세요.")
    private String phonenumber;


    private Role role;

    @Builder
    public User toEntity() {
        return User.builder()
                .email(email)
                .nickname(nickname)
                .password(password)
                .phonenumber(phonenumber)
                .role(Role.USER)
                .build();
    }
}
