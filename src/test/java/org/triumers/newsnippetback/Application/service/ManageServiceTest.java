package org.triumers.newsnippetback.Application.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.triumers.newsnippetback.domain.aggregate.entity.Quiz;
import org.triumers.newsnippetback.domain.dto.CrawlingQuizDTO;
import org.triumers.newsnippetback.domain.dto.QuizDTO;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ManageServiceTest {

    ManageService manageService;

    @Autowired
    public ManageServiceTest(ManageService manageService) {
        this.manageService = manageService;
    }

    static Stream<LocalDate> getDate() {
        return Stream.of(
                LocalDate.of(2024, 4, 1),
                LocalDate.of(2024, 4, 2),
                LocalDate.of(2024, 4, 3),
                LocalDate.of(2024, 4, 4),
                LocalDate.of(2024, 4, 5)
        );
    }

    @ParameterizedTest
    @MethodSource("getDate")
    void selectCrawlingQuizListByDate(LocalDate date){
        List<CrawlingQuizDTO> quizDTOList = manageService.selectCrawlingQuizListByDate(date);

        assertNotNull(quizDTOList);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    void selectCrawlingQuizById(int id){
        CrawlingQuizDTO quizDTO = manageService.selectCrawlingQuizByID(id);

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

    @ParameterizedTest
    @ValueSource(ints = {6, 7, 8})
    void deleteQuizInListSuccess(int id){
        QuizDTO quizDTO = manageService.deleteQuizInListById(id);
        assertNotNull(quizDTO);
    }
}