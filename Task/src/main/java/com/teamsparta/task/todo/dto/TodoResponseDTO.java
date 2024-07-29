package com.teamsparta.task.todo.dto;

import java.time.LocalDateTime;
import java.util.Date;

import com.teamsparta.task.todo.model.Todo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

public record TodoResponseDTO(
        @Schema(description = "일정 ID")
        Long id,
        @Schema(description = "제목")
        String title,
        @Schema(description = "내용")
        String content,
        @Schema(description = "담당자 이름")
        String username,
        @Schema(description = "생성 일시")
        LocalDateTime createdAt
) {

    public static TodoResponseDTO of(Todo entity) {
        return new TodoResponseDTO(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getUsername(),
                entity.getCreatedAt()
        );
    }
}
