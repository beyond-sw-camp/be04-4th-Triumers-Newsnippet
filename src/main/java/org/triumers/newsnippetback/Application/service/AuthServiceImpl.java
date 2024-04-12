package org.triumers.newsnippetback.Application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.triumers.newsnippetback.common.exception.UserEmailDuplicateException;
import org.triumers.newsnippetback.common.exception.UserNicknameDuplicateException;
import org.triumers.newsnippetback.common.exception.WrongInputTypeException;
import org.triumers.newsnippetback.common.exception.WrongPasswordException;
import org.triumers.newsnippetback.domain.aggregate.entity.User;
import org.triumers.newsnippetback.domain.aggregate.enums.UserRole;
import org.triumers.newsnippetback.domain.aggregate.enums.UserStatus;
import org.triumers.newsnippetback.domain.dto.AuthDTO;
import org.triumers.newsnippetback.domain.dto.PasswordDTO;
import org.triumers.newsnippetback.domain.dto.UserDTO;
import org.triumers.newsnippetback.domain.repository.UserRepository;
import org.triumers.newsnippetback.domain.service.ValidationAuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final ValidationAuthService validation;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, ValidationAuthService validation, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.validation = validation;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void signup(AuthDTO request) throws UserNicknameDuplicateException, UserEmailDuplicateException, WrongInputTypeException {

        // 닉네임 중복 예외 처리
        if (userRepository.existsByNickname(request.getNickname())) {
            throw new UserNicknameDuplicateException();
        }

        // 닉네임 자릿수, 타입 검증
        validation.nickname(request.getNickname());

        // 이메일 중복 예외 처리
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserEmailDuplicateException();
        }

        User user = userMapper(request);

        userRepository.save(user);
    }

    public boolean existNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    public boolean existEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void modifyUserInfo(UserDTO userDTO) throws UserNicknameDuplicateException, WrongInputTypeException {
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        if (userDTO.getNickname() != null) {
            if (existNickname(userDTO.getNickname())) {
                throw new UserNicknameDuplicateException();
            }

            // 닉네임 자릿수, 타입 검증
            validation.nickname(userDTO.getNickname());

            user.setNickname(userDTO.getNickname());
        }

        if (userDTO.getName() != null) {
            user.setName(userDTO.getName());
        }

        userRepository.save(user);
    }

    @Override
    public void modifyPassword(PasswordDTO passwordDTO) throws WrongPasswordException {
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        if (bCryptPasswordEncoder.matches(passwordDTO.getOldPassword(), user.getPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(passwordDTO.getNewPassword()));
            userRepository.save(user);
            return;
        }

        throw new WrongPasswordException();
    }

    private User userMapper(AuthDTO request) {
        User user = new User();

        user.setName(request.getName());
        user.setNickname(request.getNickname());
        user.setEmail(request.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        user.setUserRole(UserRole.USER);
        user.setProvider(request.getProvider());
        user.setSnsId(request.getSnsId());
        user.setUserStatus(UserStatus.ACTIVE);

        return user;
    }
}
