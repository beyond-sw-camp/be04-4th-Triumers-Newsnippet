package org.triumers.newsnippetback.Application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.triumers.newsnippetback.Application.service.AuthService;
import org.triumers.newsnippetback.common.exception.UserEmailDuplicateException;
import org.triumers.newsnippetback.common.exception.UserNicknameDuplicateException;
import org.triumers.newsnippetback.domain.aggregate.enums.Provider;
import org.triumers.newsnippetback.domain.aggregate.vo.RequestUserVO;
import org.triumers.newsnippetback.domain.aggregate.vo.ResponseMessageVO;
import org.triumers.newsnippetback.domain.dto.AuthDTO;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseMessageVO> signup(@RequestBody RequestUserVO request) {

        AuthDTO user = new AuthDTO();

        user.setName(request.getName());
        user.setNickname(request.getNickname());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setProvider(Provider.LOCAL);

        try {
            authService.signup(user);

        } catch (UserNicknameDuplicateException | UserEmailDuplicateException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessageVO(e.getMessage()));
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseMessageVO(user.getNickname() + "님 회원가입에 성공했습니다."));
    }

    @PostMapping("/exist/nickname")
    public ResponseEntity<ResponseMessageVO> existNickname(@RequestBody RequestUserVO request) {

        if (authService.existNickname(request.getNickname())) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessageVO("이미 존재하는 닉네임입니다."));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessageVO("사용 가능한 닉네임입니다."));
    }

    @PostMapping("/exist/email")
    public ResponseEntity<ResponseMessageVO> existEmail(@RequestBody RequestUserVO request) {

        if (authService.existEmail(request.getEmail())) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessageVO("이미 존재하는 이메일입니다."));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessageVO("사용 가능한 이메일입니다."));
    }
}
