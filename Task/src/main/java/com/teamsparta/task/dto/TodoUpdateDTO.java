package com.teamsparta.task.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoUpdateDTO {
    private String title;
    private String content;
    private String assignee;
}//한번 수정하면 비번까지 바뀌는 현상 때문에 비번을 유지하기 위해 만듬
