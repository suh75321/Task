package com.teamsparta.task.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    private String nickname; // 별명 추가

    @NotBlank(message = "사용자 이름은 필수 항목입니다.")
    @Size(min = 4, max = 10, message = "사용자 이름은 4자 이상 10자 이하이어야 합니다.")
    private String username;

    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    @Size(min = 8, max = 15, message = "비밀번호는 8자 이상 15자 이하이어야 하며, 대소문자와 숫자를 포함해야 합니다.")
    private String password;

    private String email; // 이메일 추가

}
