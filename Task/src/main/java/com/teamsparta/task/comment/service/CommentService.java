package com.teamsparta.task.comment.service;

import com.teamsparta.task.comment.dto.CommentRequestDto;
import com.teamsparta.task.comment.model.Comment;
import com.teamsparta.task.comment.repository.CommentRepository;
import com.teamsparta.task.todo.model.Todo;
import com.teamsparta.task.todo.repository.TodoRepository;
import com.teamsparta.task.user.model.User;
import com.teamsparta.task.user.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    private final TodoRepository todoRepository;

    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, TodoRepository todoRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
    }

    public List<Comment> findAllComments() {
        return commentRepository.findAll();
    }

    public Comment addComment(CommentRequestDto commentRequestDto) {
        // 예외 처리
        if (commentRequestDto.getTodoId() == null) {
            throw new IllegalArgumentException("일정 ID를 입력해 주세요.");
        }
        if (commentRequestDto.getContent() == null || commentRequestDto.getContent().isEmpty()) {
            throw new IllegalArgumentException("댓글 내용을 입력해 주세요.");
        }

        // 일정 확인
        Todo todo = todoRepository.findById(commentRequestDto.getTodoId())
                .orElseThrow(() -> new IllegalArgumentException("일정이 존재하지 않습니다."));

        // 사용자 확인
        User user = userRepository.findById(Long.valueOf(commentRequestDto.getUserId()))
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));

        // 댓글 생성
        Comment comment = new Comment();
        comment.setContent(commentRequestDto.getContent());
        comment.setTodo(todo);
        comment.setUser(user);
        return commentRepository.save(comment);
    }



    public Comment updateComment(Long commentId, CommentRequestDto commentRequest, String currentUserId) {
        // 예외 처리
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));

        // 현재 사용자와 댓글 작성자 확인
        if (!comment.getUser().getId().equals(Long.parseLong(currentUserId))) {
            throw new IllegalArgumentException("사용자가 일치하지 않습니다.");
        }

        // 댓글 내용 수정
        if (commentRequest.getContent() != null && !commentRequest.getContent().isEmpty()) {
            comment.setContent(commentRequest.getContent());
        }
        return commentRepository.save(comment);
    }


    public void deleteComment(Long commentId, String currentUserId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));

        // 현재 사용자와 댓글 작성자 확인
        if (!comment.getUser().getId().equals(Long.parseLong(currentUserId))) {
            throw new IllegalArgumentException("사용자가 일치하지 않습니다.");
        }

        commentRepository.delete(comment);
    }


}
