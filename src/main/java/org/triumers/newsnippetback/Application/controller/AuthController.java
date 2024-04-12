package org.triumers.newsnippetback.Application.controller;

import io.jsonwebtoken.ExpiredJwtException;
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
import org.triumers.newsnippetback.common.exception.WrongInputTypeException;
import org.triumers.newsnippetback.common.exception.WrongPasswordException;
import org.triumers.newsnippetback.domain.aggregate.enums.Provider;
import org.triumers.newsnippetback.domain.aggregate.vo.RequestModifyPasswordVO;
import org.triumers.newsnippetback.domain.aggregate.vo.RequestUserVO;
import org.triumers.newsnippetback.domain.aggregate.vo.ResponseMessageVO;
import org.triumers.newsnippetback.domain.dto.AuthDTO;
import org.triumers.newsnippetback.domain.dto.PasswordDTO;
import org.triumers.newsnippetback.domain.dto.UserDTO;

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

        } catch (UserNicknameDuplicateException | UserEmailDuplicateException | WrongInputTypeException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessageVO(e.getMessage()));
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseMessageVO(user.getNickname() + "님 회원가입에 성공했습니다."));
    }

    @PostMapping("/exist/nickname")
    public ResponseEntity<ResponseMessageVO> existNickname(@RequestBody RequestUserVO request) {

        if (authService.existNickname(request.getNickname())) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessageVO("[ERROR] 이미 존재하는 닉네임입니다."));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessageVO("사용 가능한 닉네임입니다."));
    }

    @PostMapping("/exist/email")
    public ResponseEntity<ResponseMessageVO> existEmail(@RequestBody RequestUserVO request) {

        if (authService.existEmail(request.getEmail())) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessageVO("[ERROR] 이미 존재하는 이메일입니다."));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessageVO("[사용 가능한 이메일입니다."));
    }

    @PostMapping("/modify/info")
    public ResponseEntity<ResponseMessageVO> modifyUserInfo(@RequestBody RequestUserVO request) {

        UserDTO userDTO = new UserDTO();
        userDTO.setName(request.getName());
        userDTO.setNickname(request.getNickname());

        try {
            authService.modifyUserInfo(userDTO);

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessageVO("변경 성공"));
        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessageVO("[ERROR] 로그인 이후 이용해주십시오."));
        } catch (UserNicknameDuplicateException | WrongInputTypeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessageVO(e.getMessage()));
        }
    }

    @PostMapping("/modify/password")
    public ResponseEntity<ResponseMessageVO> modifyPassword(@RequestBody RequestModifyPasswordVO request) {

        PasswordDTO passwordDTO = new PasswordDTO(request.getOldPassword(), request.getNewPassword());

        try {
            authService.modifyPassword(passwordDTO);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessageVO("변경 성공"));

        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessageVO("[ERROR] 로그인 이후 이용해주십시오."));
        } catch (WrongPasswordException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessageVO(e.getMessage()));
        }
    }
}
