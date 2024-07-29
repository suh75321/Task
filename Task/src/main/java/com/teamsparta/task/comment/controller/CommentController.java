package com.teamsparta.task.comment.controller;

import com.teamsparta.task.comment.dto.CommentRequestDto;
import com.teamsparta.task.comment.dto.CommentResponseDto;
import com.teamsparta.task.comment.model.Comment;
import com.teamsparta.task.comment.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "모든 댓글 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetched all comments.")
    })
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

    @Operation(summary = "댓글 추가")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created the comment.")
    })
    @PostMapping
    public ResponseEntity<CommentResponseDto> addComment(
            @RequestBody CommentRequestDto commentRequest,
            @RequestParam Long todoId
//            @RequestParam String userId
    ) {
        // 현재 로그인한 사용자 ID 가져오기. 이 밑의 세줄이 바로 RequestParam없이 인식하는것
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();

        Comment createdComment = commentService.addComment(commentRequest, todoId, currentUserId);

        CommentResponseDto responseDto = new CommentResponseDto(
                createdComment.getCommentId(),
                createdComment.getContent(),
                createdComment.getUser().getId().toString(),
                createdComment.getCreatedAt().toString()
        );

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @Operation(summary = "댓글 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated the comment."),
            @ApiResponse(responseCode = "404", description = "Comment not found.")
    })
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentRequestDto commentRequest,
            @RequestParam Long todoId
//            @RequestParam String userId
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();
        // 댓글 업데이트
        Comment updatedComment = commentService.updateComment(commentId, commentRequest, todoId, currentUserId);

        CommentResponseDto responseDto = new CommentResponseDto(
                updatedComment.getCommentId(),
                updatedComment.getContent(),
                updatedComment.getUser().getId().toString(),
                updatedComment.getCreatedAt().toString()
        );

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "댓글 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted the comment."),
            @ApiResponse(responseCode = "404", description = "Comment not found.")
    })
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
