package org.triumers.newsnippetback.Application.service;

import org.triumers.newsnippetback.domain.aggregate.vo.SolvedRequest;
import org.triumers.newsnippetback.domain.dto.SolvedDTO;

public interface SolvedService {
    SolvedDTO findSelectedOptionAndCompareAnswer(SolvedRequest solvedRequest);

    SolvedDTO findSolvedQuizByUserID(SolvedRequest solvedRequest);
}
