package com.teamsparta.task.todo.model;
//lombok를 통해 Getter,Setter,기본 생성자를 자동 생성
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@Entity// 꼭 추가하자!!
@Table(name = "todo")

public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//반드시 필요
    private Long id; // 고유 id
    private String title;// 제목
    private String content;//내용
    private String assignee;//담당자
    private String password;//비번
    private Date createdAt;//작성일
}
