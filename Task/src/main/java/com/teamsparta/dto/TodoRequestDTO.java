package com.teamsparta.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TodoRequestDTO {
    private String title;
    private String content;
    private String assignee;
    private String password;
}
