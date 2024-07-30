package com.teamsparta.task.exception;

public class InvalidArgumentException extends TodoException {
    public InvalidArgumentException() {
        super(TodoErrorCode.INVALID_ARGUMENT);
    }
}
