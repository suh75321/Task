package com.teamsparta.task.exception;

public class TodoPasswordNotMatchedException extends TodoException {

    public TodoPasswordNotMatchedException() {
        super(TodoErrorCode.PASSWORD_NOT_MATCHED);
    }
}
//이 클래스는 TodoException 클래스를 상속받습니다. TodoException은 사용자 정의 예외의 기본 클래스
//TodoPasswordNotMatchedException 생성자는 TodoErrorCode.PASSWORD_NOT_MATCHED를
// 인자로 받는 TodoException 생성자를 호출합니다. 이를 통해 예외가 발생했을 때,
// "패스워드가 불일치합니다"라는 메시지와 함께 HTTP 상태 코드 401 (Unauthorized)을 반환하게 됩니다.
