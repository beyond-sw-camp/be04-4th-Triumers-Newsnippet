package org.triumers.newsnippetback.Application.service;

import org.triumers.newsnippetback.common.exception.UserEmailDuplicateException;
import org.triumers.newsnippetback.common.exception.UserNicknameDuplicateException;
import org.triumers.newsnippetback.domain.dto.AuthDTO;

public interface AuthService {

    public void signup(AuthDTO request) throws UserNicknameDuplicateException, UserEmailDuplicateException;

    public boolean existNickname(String nickname);

    public boolean existEmail(String email);
}
