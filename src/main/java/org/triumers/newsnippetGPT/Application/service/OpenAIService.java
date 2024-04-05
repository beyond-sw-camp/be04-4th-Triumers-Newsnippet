package org.triumers.newsnippetGPT.Application.service;

import org.triumers.newsnippetGPT.common.exception.OpenAIResponseFailException;
import org.triumers.newsnippetGPT.domain.aggregate.entity.CrawlingQuiz;

import java.time.LocalDate;

public interface OpenAIService {

    CrawlingQuiz generateQuiz(String prompt, String newsLink, LocalDate newsDate, String categoryName) throws OpenAIResponseFailException;
}
