package org.triumers.newsnippetGPT.common.exception;

public class CustomException extends Exception {
    public CustomException(String message) {
        super("[ERROR] " + message);
    }
}
