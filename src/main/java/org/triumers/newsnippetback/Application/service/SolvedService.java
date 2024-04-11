package org.triumers.newsnippetback.Application.service;

import org.triumers.newsnippetback.domain.aggregate.entity.Solved;
import org.triumers.newsnippetback.domain.aggregate.vo.SolvedRequest;
import org.triumers.newsnippetback.domain.aggregate.vo.SolvedResultRequest;
import org.triumers.newsnippetback.domain.dto.SolvedDTO;

import java.util.List;

public interface SolvedService {
    SolvedDTO findSelectedOptionAndCompareAnswer(SolvedRequest solvedRequest);

    SolvedDTO findSolvedQuizByUserIdAndQuizId(SolvedRequest solvedRequest);

    List<Solved> findSolvedQuizListByUserId(SolvedRequest solvedRequest);

    List<Solved> findCorrectQuizByUserIdAndSolvedDate(SolvedResultRequest solvedResultRequest);

    List<SolvedDTO> findSolvedQuizListByUserIdAndDate(SolvedRequest solvedRequest);
}
