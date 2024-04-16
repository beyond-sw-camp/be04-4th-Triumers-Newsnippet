package org.triumers.newsnippetback.Application.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.triumers.newsnippetback.Application.dto.QuizDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QuizServiceImplTest {

    private final QuizServiceImpl quizService;

    @Autowired
    public QuizServiceImplTest(QuizServiceImpl quizService) {
        this.quizService = quizService;
    }

    static Stream<LocalDate> getDate() {
        return Stream.of(
                LocalDate.of(2024, 4, 2),
                LocalDate.of(2024, 4, 9)
        );
    }

    @ParameterizedTest
    @MethodSource("getDate")
    void findAllQuizzesByDate(LocalDate date) {
        List<QuizDTO> quizDTOList = quizService.findAllQuizzesByDate(date);

        assertNotNull(quizDTOList);
        assertFalse(quizDTOList.isEmpty());
    }

    @Test
    void findAllQuizzesByDateException() {
        LocalDate date = LocalDate.of(2020, 1, 1);

        assertThrows(NoSuchElementException.class, () -> {
            quizService.findAllQuizzesByDate(date);
        });
    }

}