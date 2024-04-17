package org.triumers.newsnippetback.common.exception;

public class UserNotFoundException extends CustomException {
    public UserNotFoundException() {
        super("존재하지 않는 유저입니다.");
    }
}
