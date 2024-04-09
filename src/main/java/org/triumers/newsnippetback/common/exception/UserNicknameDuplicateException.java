package org.triumers.newsnippetback.common.exception;

public class UserNicknameDuplicateException extends CustomException {
    public UserNicknameDuplicateException() {
        super("닉네임 중복");
    }
}
