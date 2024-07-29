package com.teamsparta.task.exception;

import com.teamsparta.task.exception.ErrorResponse;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleTodoException(
            TodoException exception
    ) {
        var status = exception.getErrorCode().getStatus();
        var errorResponse = new ErrorResponse(
                exception.getErrorCode().name(),
                exception.getErrorCode().getMessage()
        );
        return ResponseEntity.status(status).body(errorResponse);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception
    ) {
        var message = exception.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining());
        var errorResponse = new ErrorResponse(
                "ARGUMENT_NOT_VALID",
                message
        );

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException exception) {
        var errorResponse = new ErrorResponse(
                "INTERNAL_SERVER_ERROR",
                "서버 에러입니다"
        );
        return ResponseEntity.internalServerError().body(errorResponse);
    }
}

