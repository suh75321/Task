package com.teamsparta.task.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {
    private Long id;  // 댓글 ID
    private String content;  // 댓글 내용
    private String userId;  // 작성자 ID
    private String createdAt;  // 작성일시
}

    // 생성자
//    public CommentResponseDto(Long id, String content, String userId, String createdAt) {
//        this.id = id;
//        this.content = content;
//        this.userId = userId;
//        this.createdAt = createdAt;


