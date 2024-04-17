package org.triumers.newsnippetback.Application.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.triumers.newsnippetback.domain.aggregate.entity.Quiz;
import org.triumers.newsnippetback.Application.dto.CrawlingQuizDTO;
import org.triumers.newsnippetback.Application.dto.QuizDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ManageServiceTest {

    private final ManageService manageService;

    @Autowired
    public ManageServiceTest(ManageService manageService) {
        this.manageService = manageService;
    }

    static Stream<LocalDate> getDate() {
        return Stream.of(
                LocalDate.of(2024, 4, 2),
                LocalDate.of(2024, 4, 9)
        );
    }

    @ParameterizedTest
    @MethodSource("getDate")
    void selectCrawlingQuizListByDate(LocalDate date){
        List<CrawlingQuizDTO> quizDTOList = manageService.selectCrawlingQuizListByDate(date);

        assertNotNull(quizDTOList);
    }

    @ParameterizedTest
    @ValueSource(ints = {11, 12, 13})
    void selectCrawlingQuizById(int id){
        CrawlingQuizDTO quizDTO = manageService.selectCrawlingQuizByID(id);

        assertNotNull(quizDTO);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void addSelectedQuiz(int id){
        Quiz savedQuiz = manageService.insertSelectedQuizById(id);

        assertNotNull(savedQuiz);
    }

    @Test
    void selectQuizListByDate(){
        List<QuizDTO> savedQuizList = manageService.selectQuizListByDate(LocalDate.now().plusDays(1));

        assertNotNull(savedQuizList);
    }

    @ParameterizedTest
    @ValueSource(ints = {13, 14, 15})
    void deleteQuizInListSuccess(int id){
        QuizDTO quizDTO = manageService.deleteQuizInListById(id);
        assertNotNull(quizDTO);
    }

    @Test
    void deleteQuizInListException(){
        int id = -1;

        assertThrows(IllegalAccessError.class, () -> {
            manageService.deleteQuizInListById(id);
        } );
    }

    @Test
    void selectCrawlingQuizByIdException(){
        int id = -1;

        assertThrows(IllegalAccessError.class, () -> {
            manageService.selectCrawlingQuizByID(id);
        } );
    }

    @Test
    void selectCrawlingQuizListByDateException(){
        LocalDate date = LocalDate.of(2020, 1, 1);

        assertThrows(NoSuchElementException.class, () -> {
            manageService.selectCrawlingQuizListByDate(date);
        } );
    }

    @Test
    void selectQuizListByDateException(){
        LocalDate date = LocalDate.of(2020, 1, 1);

        assertThrows(NoSuchElementException.class, () -> {
            manageService.selectQuizListByDate(date);
        } );
    }

}