package org.triumers.newsnippetback.Application.service;

import org.triumers.newsnippetback.domain.aggregate.vo.QuizRequest;
import org.triumers.newsnippetback.domain.dto.QuizDTO;

import java.time.LocalDate;
import java.util.List;

public interface QuizService {

    QuizDTO findQuizByDateAndNo(QuizRequest quizRequest);

}
