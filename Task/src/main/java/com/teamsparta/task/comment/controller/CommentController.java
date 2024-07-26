package com.teamsparta.task.comment.controller;

import com.teamsparta.task.comment.dto.CommentRequestDto;
import com.teamsparta.task.comment.dto.CommentResponseDto;
import com.teamsparta.task.comment.model.Comment;
import com.teamsparta.task.comment.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> getComments() {
        List<Comment> comments = commentService.findAllComments();
        List<CommentResponseDto> responseDtos = comments.stream()
                .map(comment -> new CommentResponseDto(
                        comment.getCommentId(),
                        comment.getContent(),
                        comment.getUser().getId().toString(),
                        comment.getCreatedAt().toString()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseDtos);
    }


    @PostMapping
    public ResponseEntity<CommentResponseDto> addComment(@RequestBody CommentRequestDto commentRequest) {
        Comment createdComment = commentService.addComment(commentRequest);

        CommentResponseDto responseDto = new CommentResponseDto(
                createdComment.getCommentId(),
                createdComment.getContent(),
                createdComment.getUser().getId().toString(),
                createdComment.getCreatedAt().toString()
        );

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto commentRequest) {
        // 현재 로그인한 사용자 ID 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName(); // 사용자 ID 추출

        // 댓글 업데이트
        Comment updatedComment = commentService.updateComment(commentId, commentRequest, currentUserId);

        CommentResponseDto responseDto = new CommentResponseDto(
                updatedComment.getCommentId(),
                updatedComment.getContent(),
                updatedComment.getUser().getId().toString(),
                updatedComment.getCreatedAt().toString()
        );

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }


    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
        // 현재 로그인한 사용자 ID 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName(); // 사용자 ID 추출

        // 댓글 삭제
        commentService.deleteComment(commentId, currentUserId);
        return new ResponseEntity<>("댓글이 삭제되었습니다.", HttpStatus.OK);
    }

}
