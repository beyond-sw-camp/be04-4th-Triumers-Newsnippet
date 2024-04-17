package org.triumers.newsnippetback.common.exception;

public class WrongPasswordException extends CustomException {
    public WrongPasswordException() {
        super("잘못된 비밀번호입니다.");
    }
}
