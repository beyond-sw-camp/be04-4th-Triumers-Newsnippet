package org.triumers.newsnippetback.domain.service;

import org.springframework.stereotype.Service;
import org.triumers.newsnippetback.common.exception.WrongInputTypeException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ValidationAuthService {

    public void nickname(String nickname) throws WrongInputTypeException {
        Pattern pattern = Pattern.compile("^[ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9]{2,10}$");
        Matcher matcher = pattern.matcher(nickname);

        if (!matcher.matches()) {
            throw new WrongInputTypeException();
        }
    }
}
