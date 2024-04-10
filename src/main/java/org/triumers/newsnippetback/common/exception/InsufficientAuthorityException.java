package org.triumers.newsnippetback.common.exception;

public class InsufficientAuthorityException extends CustomException {
    public InsufficientAuthorityException() {
        super("접근 권한이 없습니다.");
    }
}
