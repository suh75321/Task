package com.teamsparta.task.todo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record SimplifiedTodoResponseDTO(
        @Schema(description = "일정 ID")
        Long id,
        @Schema(description = "제목")
        String title,
        @Schema(description = "생성 일시")
        LocalDateTime createdAt
) {//일반조회가 아이디, 제목, 내용, 날짜를 보여주나 전체조회는 아이디, 제목 날짜로 간소화

}


