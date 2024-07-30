package com.teamsparta.task.exception;

public class CommentNotFoundException extends TodoException {
    public CommentNotFoundException() {
        super(TodoErrorCode.COMMENT_NOT_FOUND);
    }
}
