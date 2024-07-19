package com.teamsparta.task.dto;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TodoResponseDTO {
    private Long id;//id 추가
    private String title;
    private String content;
    private String assignee;
    private Date creationDate;
}
