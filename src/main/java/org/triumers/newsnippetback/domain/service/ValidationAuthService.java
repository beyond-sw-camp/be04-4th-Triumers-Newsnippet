package org.triumers.newsnippetback.domain.service;

import org.triumers.newsnippetback.common.exception.WrongInputTypeException;

public interface ValidationAuthService {

    void nickname(String nickname) throws WrongInputTypeException;

    void password(String password) throws WrongInputTypeException;
}
