package com.teamsparta.task.comment.model;

import com.teamsparta.task.todo.model.Todo;
import com.teamsparta.task.user.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "todo_id", nullable = false)
    private Todo todo;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt; // 생성일

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
