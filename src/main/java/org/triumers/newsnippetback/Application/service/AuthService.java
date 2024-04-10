package org.triumers.newsnippetback.Application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.triumers.newsnippetback.common.exception.UserEmailDuplicateException;
import org.triumers.newsnippetback.common.exception.UserNicknameDuplicateException;
import org.triumers.newsnippetback.domain.aggregate.entity.User;
import org.triumers.newsnippetback.domain.aggregate.enums.UserRole;
import org.triumers.newsnippetback.domain.aggregate.enums.UserStatus;
import org.triumers.newsnippetback.domain.dto.SignupDTO;
import org.triumers.newsnippetback.domain.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AuthService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void signup(SignupDTO request) throws UserNicknameDuplicateException, UserEmailDuplicateException {

        // 닉네임 중복 예외 처리
        if (userRepository.existsByNickname(request.getNickname())) {
            throw new UserNicknameDuplicateException();
        }

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



    private User userMapper(SignupDTO request) {
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
