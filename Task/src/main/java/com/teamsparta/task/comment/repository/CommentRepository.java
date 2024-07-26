package com.teamsparta.task.comment.repository;

import com.teamsparta.task.comment.model.Comment;
import com.teamsparta.task.todo.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    //List<Comment> findByTodo(Todo todo); // 특정 Todo에 속하는 댓글 조회
}
