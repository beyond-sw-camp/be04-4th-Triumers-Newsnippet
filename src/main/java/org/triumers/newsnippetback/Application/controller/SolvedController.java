package org.triumers.newsnippetback.Application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.triumers.newsnippetback.Application.service.SolvedService;
import org.triumers.newsnippetback.domain.aggregate.vo.SolvedQuizResponse;
import org.triumers.newsnippetback.domain.aggregate.vo.SolvedRequest;
import org.triumers.newsnippetback.domain.aggregate.vo.SolvedIsCorrectResponse;
import org.triumers.newsnippetback.domain.dto.SolvedDTO;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/solved")
public class SolvedController {
    private final SolvedService solvedService;

    @Autowired
    public SolvedController(SolvedService solvedService) {
        this.solvedService = solvedService;
    }

    /* 설명. 1. 사용자가 입력한 답과 문제의 정답 여부 판단 */
    @PostMapping("/check")
    public ResponseEntity<SolvedIsCorrectResponse> findSelectedOptionAndCompareAnswer(@RequestBody SolvedRequest solvedRequest) {
        try {
            SolvedDTO solvedDTO = solvedService.findSelectedOptionAndCompareAnswer(solvedRequest);
            SolvedIsCorrectResponse solvedIsCorrectResponse = new SolvedIsCorrectResponse();
            solvedIsCorrectResponse.setUserId(solvedDTO.getUserId());
            solvedIsCorrectResponse.setQuizId(solvedDTO.getQuizId());
            solvedIsCorrectResponse.setCorrect(solvedDTO.isCorrect());

            return ResponseEntity.ok().body(solvedIsCorrectResponse);
        } catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /* 설명. 2. 사용자가 과거에 풀었던 문제 조회 */
    @PostMapping("/find")
    public ResponseEntity<SolvedQuizResponse> findSolvedQuizByUserID(@RequestBody SolvedRequest solvedRequest){
        try {
            SolvedDTO solvedDTO = solvedService.findSolvedQuizByUserID(solvedRequest);
            SolvedQuizResponse solvedQuizResponse = new SolvedQuizResponse();
            solvedQuizResponse.setUserId(solvedDTO.getUserId());
            solvedQuizResponse.setQuizId(solvedDTO.getQuizId());
            solvedQuizResponse.setCategoryId(solvedDTO.getCategoryId());
            solvedQuizResponse.setContent(solvedDTO.getContent());
            solvedQuizResponse.setOptionA(solvedDTO.getOptionA());
            solvedQuizResponse.setOptionB(solvedDTO.getOptionB());
            solvedQuizResponse.setOptionC(solvedDTO.getOptionC());
            solvedQuizResponse.setOptionD(solvedDTO.getOptionD());
            solvedQuizResponse.setAnswer(solvedDTO.getAnswer());
            solvedQuizResponse.setExplanation(solvedDTO.getExplanation());
            solvedQuizResponse.setNewsLink(solvedDTO.getNewsLink());
            solvedQuizResponse.setDate(solvedDTO.getDate());

            return ResponseEntity.ok().body(solvedQuizResponse);
        } catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
