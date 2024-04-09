package org.triumers.newsnippetback.Application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.triumers.newsnippetback.Application.service.SignupService;
import org.triumers.newsnippetback.common.exception.UserEmailDuplicateException;
import org.triumers.newsnippetback.common.exception.UserNicknameDuplicateException;
import org.triumers.newsnippetback.domain.aggregate.enums.Provider;
import org.triumers.newsnippetback.domain.aggregate.vo.RequestSignupVO;
import org.triumers.newsnippetback.domain.aggregate.vo.ResponseMessageVO;
import org.triumers.newsnippetback.domain.dto.SignupDTO;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final SignupService signupService;

    @Autowired
    public AuthController(SignupService signupService) {
        this.signupService = signupService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseMessageVO> signup(@RequestBody RequestSignupVO request) {

        ResponseMessageVO response = new ResponseMessageVO();

        SignupDTO user = new SignupDTO();

        user.setName(request.getName());
        user.setNickname(request.getNickname());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setProvider(Provider.LOCAL);

        try {
            signupService.signup(user);

            response.setMessage(user.getNickname() + "님 회원가입에 성공했습니다.");

        } catch (UserNicknameDuplicateException | UserEmailDuplicateException e) {
            response.setMessage(e.getMessage());
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
