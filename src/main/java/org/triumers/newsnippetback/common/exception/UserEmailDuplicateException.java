package org.triumers.newsnippetback.common.exception;

public class UserEmailDuplicateException extends CustomException {
    public UserEmailDuplicateException() {
        super("이메일 중복");
    }
}
