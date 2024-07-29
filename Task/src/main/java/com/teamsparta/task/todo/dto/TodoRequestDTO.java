package com.teamsparta.task.todo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


public record TodoRequestDTO(
        @Schema(description = "제목")
        @Size(min = 1, max = 200)
        @NotBlank
        String title,

        @Schema(description = "내용")
        String content,

        @Schema(description = "담당자 이메일")
        @NotBlank
        @Email
        String username,

        @Schema(description = "비밀번호")
        @NotBlank
        String password
) {

}
