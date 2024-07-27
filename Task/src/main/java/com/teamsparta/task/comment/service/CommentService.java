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

    public Comment addComment(CommentRequestDto commentRequestDto, Long todoId, String userId) {
        // 예외 처리
        //예외 처리: 댓글 내용이 비어 있는 경우
        if (commentRequestDto.getContent() == null || commentRequestDto.getContent().isEmpty()) {
            throw new IllegalArgumentException("댓글 내용을 입력해 주세요.");
        }

        // 예외 처리: 선택한 일정의 ID를 입력받지 않은 경우
        if (todoId == null) {
            throw new IllegalArgumentException("일정 ID를 입력해 주세요.");
        }

        // 일정 확인, 예외 처리: 일정이 DB에 저장되지 않은 경우
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new IllegalArgumentException("일정이 존재하지 않습니다."));

        // 사용자 확인, 예외 처리: 사용자가 DB에 저장되지 않은 경우
        User user = userRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));

        // 댓글 생성
        Comment comment = new Comment();
        comment.setContent(commentRequestDto.getContent());
        comment.setTodo(todo);
        comment.setUser(user);
        return commentRepository.save(comment);
    }




    public Comment updateComment(Long commentId, CommentRequestDto commentRequest, Long todoId, String userId) {
        // 예외 처리, 댓글이 비어있는 경우
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));


        // 일정 확인. Todo 2에 만든 댓글을 todo1 이라고해도 수정되는거 막기
        Todo todo = comment.getTodo();
        if (!Objects.equals(todo.getId(), todoId)) {
            throw new IllegalArgumentException("댓글이 해당 일정에 속하지 않습니다.");
        }

        // 사용자 확인
        User user = userRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));

        // 현재 사용자와 댓글 작성자 확인
        if (!comment.getUser().getId().equals(Long.parseLong(userId))) {
            throw new IllegalArgumentException("사용자가 일치하지 않습니다.");
        }

        // 댓글 내용 수정
        if (commentRequest.getContent() != null && !commentRequest.getContent().isEmpty()) {
            comment.setContent(commentRequest.getContent());
        }



//        comment.setTodo(todo);
//        comment.setUser(user); 이것들 있으면 안됨 댓글 수정하면 2번일정에 있던 댓글이 1번으로 감
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
