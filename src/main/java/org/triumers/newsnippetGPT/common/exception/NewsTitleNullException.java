package org.triumers.newsnippetGPT.common.exception;

public class NewsTitleNullException extends CustomException {
    public NewsTitleNullException() {
        super("뉴스 제목이 존재하지 않음");
    }
}
