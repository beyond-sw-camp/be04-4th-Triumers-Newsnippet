package org.triumers.newsnippetGPT.common.exception;

public class NewsContentNullException extends CustomException {
    public NewsContentNullException() {
        super("뉴스 내용이 존재하지 않음");
    }
}
