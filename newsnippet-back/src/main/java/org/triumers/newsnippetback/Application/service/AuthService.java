package org.triumers.newsnippetback.Application.service;

import org.triumers.newsnippetback.common.exception.UserEmailDuplicateException;
import org.triumers.newsnippetback.common.exception.UserNicknameDuplicateException;
import org.triumers.newsnippetback.common.exception.WrongInputTypeException;
import org.triumers.newsnippetback.common.exception.WrongPasswordException;
import org.triumers.newsnippetback.Application.dto.AuthDTO;
import org.triumers.newsnippetback.Application.dto.PasswordDTO;
import org.triumers.newsnippetback.Application.dto.UserDTO;

public interface AuthService {

    void signup(AuthDTO request) throws UserNicknameDuplicateException, UserEmailDuplicateException, WrongInputTypeException;

    boolean existNickname(String nickname);

    boolean existEmail(String email);

    void modifyUserInfo(UserDTO userDTO) throws UserNicknameDuplicateException, WrongInputTypeException;

    void modifyPassword(PasswordDTO passwordDTO) throws WrongPasswordException, WrongInputTypeException;

    void updateSolvedQuiz(boolean isCorrect);

    public int getUserId();
}
