package org.triumers.newsnippetGPT.Application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.triumers.newsnippetGPT.common.exception.NewsContentNullException;
import org.triumers.newsnippetGPT.common.exception.NewsTitleNullException;
import org.triumers.newsnippetGPT.common.exception.OpenAIResponseFailException;
import org.triumers.newsnippetGPT.domain.aggregate.entity.CrawlingQuiz;
import org.triumers.newsnippetGPT.domain.dto.NewsDTO;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OpenAIServiceImplTest {

    @Autowired
    private PromptService promptService;
    @Autowired
    private OpenAIService openAIService;

    @Test
    @DisplayName("문제 생성 테스트")
    void generateQuiz() throws NewsTitleNullException, NewsContentNullException, OpenAIResponseFailException {
        // given
        NewsDTO news = new NewsDTO();
        news.setTitle("뉴스 제목 테스트");
        news.setContent("뉴스 내용 테스트");
        String prompt = promptService.createPrompt(news);

        // when
        CrawlingQuiz result = openAIService.generateQuiz(prompt, "www.news.com",
                LocalDate.of(2024, 04, 05), "테스트용 카테고리");

        System.out.println("result = " + result);

        // then
        assertNotNull(result.getContent());
        assertNotNull(result.getOptionA());
        assertNotNull(result.getOptionB());
        assertNotNull(result.getOptionC());
        assertNotNull(result.getOptionD());
        assertNotNull(result.getAnswer());
        assertNotNull(result.getExplanation());
        assertNotNull(result.getNewsLink());
        assertNotNull(result.getNewsDate());
    }
}