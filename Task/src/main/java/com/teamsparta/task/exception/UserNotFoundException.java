package com.teamsparta.task.exception;


public class UserNotFoundException extends TodoException {
    public UserNotFoundException() {
        super(TodoErrorCode.USER_NOT_FOUND);
    }
}


