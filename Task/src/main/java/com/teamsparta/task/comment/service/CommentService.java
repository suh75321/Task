package com.teamsparta.task.comment.service;

import com.teamsparta.task.exception.TodoNotFoundException;
import com.teamsparta.task.comment.dto.CommentRequestDto;
import com.teamsparta.task.comment.model.Comment;
import com.teamsparta.task.comment.repository.CommentRepository;
import com.teamsparta.task.exception.CommentNotFoundException;
import com.teamsparta.task.exception.InvalidArgumentException;
import com.teamsparta.task.exception.UserNotFoundException;
import com.teamsparta.task.todo.model.Todo;
import com.teamsparta.task.todo.repository.TodoRepository;
import com.teamsparta.task.user.model.User;
import com.teamsparta.task.user.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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

    // 새로운 댓글을 추가합니다.
    public Comment addComment(CommentRequestDto commentRequestDto, Long todoId, String userId) {
        // 댓글 내용이 비어있는지 확인하고, 비어있다면 InvalidArgumentException(잘못된 접근방식입니다.)
        if (commentRequestDto.getContent() == null || commentRequestDto.getContent().isEmpty()) {
            throw new InvalidArgumentException();
        }
        // todoId가 null인지 확인하고, null이라면 InvalidArgumentException
        if (todoId == null) {
            throw new InvalidArgumentException();
        }

        // 주어진 todoId에 해당하는 Todo를 조회하고, 존재하지 않으면 TodoNotFoundException(할일을 찾을 수 없습니다.)
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(TodoNotFoundException::new);

        // 주어진 userId에 해당하는 User를 조회하고, 존재하지 않으면 UserNotFoundException(사용자를 찾을 수 없습니다.)
        User user = userRepository.findById(Long.valueOf(userId))
                .orElseThrow(UserNotFoundException::new);

        // 댓글 엔티티로 Comment 객체를 생성하여 저장
        Comment comment = Comment.builder()
                .content(commentRequestDto.getContent())
                .todo(todo)
                .user(user)
                .build();

        // 생성된 Comment 객체를 저장하고 반환
        return commentRepository.save(comment);
    }

    public Comment updateComment(Long commentId, CommentRequestDto commentRequest, Long todoId, String userId) {
        // 주어진 commentId에 해당하는 Comment를 조회하고, 존재하지 않으면 CommentNotFoundException
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);

        // 주어진 todoId가 해당 Comment의 Todo와 일치하는지 확인하고, 일치하지 않으면 InvalidArgumentException
        Todo todo = comment.getTodo();
        if (!Objects.equals(todo.getId(), todoId)) {
            throw new InvalidArgumentException();
        }

        // 주어진 userId에 해당하는 User를 조회하고, 존재하지 않으면 UserNotFoundException
        User user = userRepository.findById(Long.valueOf(userId))
                .orElseThrow(UserNotFoundException::new);

        // 현재 사용자(userId)가 해당 Comment의 작성자인지 확인하고, 일치하지 않으면 InvalidArgumentException
        if (!comment.getUser().getId().equals(Long.parseLong(userId))) {
            throw new InvalidArgumentException();
        }

        // 댓글 내용이 비어있지 않으면 업데이트
        if (commentRequest.getContent() != null && !commentRequest.getContent().isEmpty()) {
            comment.update(commentRequest.getContent());
        }

        // 업데이트된 Comment 객체를 저장하고 반환
        return commentRepository.save(comment);
    }

    public void deleteComment(Long commentId, String currentUserId) {
        // 주어진 commentId에 해당하는 Comment를 조회하고, 존재하지 않으면 CommentNotFoundException.
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);

        // 현재 사용자(currentUserId)가 해당 Comment의 작성자인지 확인하고, 일치하지 않으면 InvalidArgumentException
        if (!comment.getUser().getId().equals(Long.parseLong(currentUserId))) {
            throw new InvalidArgumentException();
        }

        commentRepository.delete(comment);
    }
}
