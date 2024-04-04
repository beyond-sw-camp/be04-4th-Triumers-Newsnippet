package org.triumers.newsnippetback.Application.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.triumers.newsnippetback.domain.aggregate.entity.Quiz;
import org.triumers.newsnippetback.domain.dto.CrawlingQuizDTO;
import org.triumers.newsnippetback.domain.dto.QuizDTO;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ManageServiceTest {

    ManageService manageService;

    @Autowired
    public ManageServiceTest(ManageService manageService) {
        this.manageService = manageService;
    }

    @Test
    void selectCrawlingQuizListByDate(){
        List<CrawlingQuizDTO> quizDTOList = manageService.selectCrawlingQuizListByDate(LocalDate.of(2024,4,2));

        assertNotNull(quizDTOList);
    }

    @Test
    void selectCrawlingQuizById(){
        CrawlingQuizDTO quizDTO = manageService.selectCrawlingQuizByID(1);

        assertNotNull(quizDTO);
    }

    @Test
    void addSelectedQuiz(){
        List<CrawlingQuizDTO> selectedCrawlingQuizList = manageService.selectCrawlingQuizListByDate(LocalDate.of(2024,4,2));
        List<Quiz> savedQuizList = manageService.insertSelectedQuiz(selectedCrawlingQuizList);

        assertNotNull(savedQuizList);
    }

    @Test
    void selectQuizListByDate(){
        List<QuizDTO> savedQuizList = manageService.selectQuizListByDate(LocalDate.now().plusDays(1));
        assertNotNull(savedQuizList);
    }

    @Test
    void deleteQuizInList(){
        QuizDTO quizDTO = manageService.deleteQuizInListById(1);
        assertNotNull(quizDTO);
    }

}