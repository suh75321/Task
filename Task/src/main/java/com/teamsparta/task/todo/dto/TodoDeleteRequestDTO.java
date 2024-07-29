package com.teamsparta.task.todo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record TodoDeleteRequestDTO (
        @Schema(description = "비밀번호")
        @NotBlank
        String password
) {

}
