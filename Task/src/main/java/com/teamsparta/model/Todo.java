package com.teamsparta.model;
//lombok를 통해 Getter,Setter,기본 생성자를 자동 생성
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
public class Todo {

    private String title;// 제목
    private String content;//내용
    private String assignee;//담당자
    private String password;//비번
    private Date creationDate;//작성일

}
