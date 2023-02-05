package com.dku.springstudy.dto.user.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SignUpRequestDto {

    @Schema(example = "abc123@gmail.com", description = "이메일(ID)")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @Schema(example = "abc123!", description = "비밀번호")
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대/소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자여야 합니다.")
    private String password;

    @Schema(example = "이수정", description = "이름")
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @Schema(example = "010-1234-5678", description = "폰번호")
    @NotBlank(message = "폰번호를 입력해주세요.")
    @Pattern(regexp = "[0-9]{3}-+[0-9]{4}-+[0-9]{4}", message = "폰번호는 010-XXXX-XXXX 형태여야 합니다.")
    private String phoneNumber;

    @Schema(example = "sujeong", description = "닉네임")
    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(min = 2, message = "닉네임이 너무 짧습니다.")
    private String nickname;
}
