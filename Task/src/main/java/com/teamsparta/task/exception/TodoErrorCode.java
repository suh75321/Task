package com.teamsparta.task.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum TodoErrorCode {
    PASSWORD_NOT_MATCHED(HttpStatus.UNAUTHORIZED, "패스워드가 불일치합니다."),
    TODO_NOT_FOUND(HttpStatus.NOT_FOUND, "할일을 찾을 수 없습니다."),

    //새로운 에러코드 3 추가
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    INVALID_ARGUMENT(HttpStatus.BAD_REQUEST, "잘못된 접근방식입니다.");

    private final HttpStatus status;
    private final String message;

}//TodoErrorCode로 에러 메시지를 정할수 있다.