package com.teamsparta.task.exception;

public class TodoNotFoundException extends TodoException {

    public TodoNotFoundException() {
        super(TodoErrorCode.TODO_NOT_FOUND);
    }
}
//TodoNotFoundException은 TodoException을 상속받아 특정 예외(TODO_NOT_FOUND)를 처리하는 클래스
//TodoPasswordNotMatchedException은 TodoException을 상속받아 다른 특정 예외(PASSWORD_NOT_MATCHED)를 처리