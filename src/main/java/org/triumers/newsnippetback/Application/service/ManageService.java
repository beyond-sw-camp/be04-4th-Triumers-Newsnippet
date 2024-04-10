package org.triumers.newsnippetback.Application.service;

import org.triumers.newsnippetback.domain.aggregate.entity.Quiz;
import org.triumers.newsnippetback.domain.dto.CrawlingQuizDTO;
import org.triumers.newsnippetback.domain.dto.QuizDTO;

import java.time.LocalDate;
import java.util.List;

public interface ManageService {
    List<CrawlingQuizDTO> selectCrawlingQuizListByDate(LocalDate date);

    CrawlingQuizDTO selectCrawlingQuizByID(int id);

    List<QuizDTO> selectQuizListByDate(LocalDate localDate);

    Quiz insertSelectedQuizById(int id);

    QuizDTO deleteQuizInListById(int id);
}
