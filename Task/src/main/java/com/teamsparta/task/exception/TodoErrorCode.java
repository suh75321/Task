package com.teamsparta.task.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum TodoErrorCode {
    PASSWORD_NOT_MATCHED(HttpStatus.UNAUTHORIZED, "패스워드가 불일치합니다."),
    TODO_NOT_FOUND(HttpStatus.NOT_FOUND, "투두를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;

}//TodoErrorCode는 특정 예외 상황에 대한 오류 코드와 메시지를 정의한 열거형(enum)입니다.
// 각 항목은 HTTP 상태 코드와 메시지를 포함합니다. TodoException과 연결