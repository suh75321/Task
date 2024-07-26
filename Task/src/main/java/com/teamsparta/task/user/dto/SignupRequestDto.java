package com.teamsparta.task.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    private String nickname; // 별명 추가
    private String username;
    private String password;
}
