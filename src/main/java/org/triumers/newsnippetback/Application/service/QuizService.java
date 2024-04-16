package org.triumers.newsnippetback.Application.service;

import org.triumers.newsnippetback.Application.dto.QuizDTO;

import java.time.LocalDate;
import java.util.List;

public interface QuizService {

    List<QuizDTO> findAllQuizzesByDate(LocalDate date);


}
