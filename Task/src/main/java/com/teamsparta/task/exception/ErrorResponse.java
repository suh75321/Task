package com.teamsparta.task.exception;

public record ErrorResponse(
        String errorCode,
        String errorMessage
) {//예외 발생 시 반환될 오류 응답.errorCode와 errorMessage로 나뉜다

}
