package org.triumers.newsnippetback.Application.service;

import org.triumers.newsnippetback.common.exception.UserEmailDuplicateException;
import org.triumers.newsnippetback.common.exception.UserNicknameDuplicateException;
import org.triumers.newsnippetback.domain.dto.AuthDTO;

public interface AuthService {

    void signup(AuthDTO request) throws UserNicknameDuplicateException, UserEmailDuplicateException;

    boolean existNickname(String nickname);

    boolean existEmail(String email);
}
