package com.teamsparta.task.exception;

import lombok.Getter;

@Getter
public abstract class TodoException extends RuntimeException {

    private final TodoErrorCode errorCode;

    public TodoException(TodoErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}//TodoErrorCode를 받아서 초기화하며,예외 메시지는 TodoErrorCode의 메시지를 사용
// (패스워드가 불일치합니다,투두를 찾을 수 없습니다 등)
