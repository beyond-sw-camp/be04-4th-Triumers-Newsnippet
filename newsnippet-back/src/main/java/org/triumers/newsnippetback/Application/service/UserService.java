package org.triumers.newsnippetback.Application.service;

import org.triumers.newsnippetback.common.exception.UserNotFoundException;
import org.triumers.newsnippetback.Application.dto.UserDTO;

public interface UserService {

    UserDTO findUserByEmail(String email) throws UserNotFoundException;

    UserDTO findUserById(int id) throws UserNotFoundException;

    UserDTO findUserByNickname(String nickname) throws UserNotFoundException;

    UserDTO findByToken() throws UserNotFoundException;
}
