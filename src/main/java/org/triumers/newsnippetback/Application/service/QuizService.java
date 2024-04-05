package org.triumers.newsnippetback.Application.service;

import org.triumers.newsnippetback.domain.aggregate.vo.QuizRequest;
import org.triumers.newsnippetback.domain.dto.QuizDTO;

public interface QuizService {

    QuizDTO findQuizByDateAndNo(QuizRequest quizRequest);

    QuizDTO findQuizAnswerByDateAndNo(QuizRequest quizRequest);
}
