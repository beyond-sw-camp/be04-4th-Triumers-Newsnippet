package org.triumers.newsnippetGPT.domain.service;

import org.springframework.stereotype.Service;
import org.triumers.newsnippetGPT.common.exception.OpenAIResponseFailException;

import java.util.Map;

@Service
public class AIResponseValidationService {

    public void aiResponseValidation(Map<String, String> response) throws OpenAIResponseFailException {

        if (!response.get("explanation").contains("기사 요약")) {
            throw new OpenAIResponseFailException("기사 요약 없음");
        }
        if (!response.get("content").contains("문제 내용")) {
            throw new OpenAIResponseFailException("문제 내용 없음");
        }
        if (!response.get("optionA").contains("A")) {
            throw new OpenAIResponseFailException("보기 A 없음");
        }
        if (!response.get("optionB").contains("B")) {
            throw new OpenAIResponseFailException("보기 B 없음");
        }
        if (!response.get("optionC").contains("C")) {
            throw new OpenAIResponseFailException("보기 C 없음");
        }
        if (!response.get("optionD").contains("D")) {
            throw new OpenAIResponseFailException("보기 D 없음");
        }
    }
}
