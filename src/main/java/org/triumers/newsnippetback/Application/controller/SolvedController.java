package org.triumers.newsnippetback.Application.controller;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.triumers.newsnippetback.Application.service.SolvedService;
import org.triumers.newsnippetback.domain.aggregate.entity.Solved;
import org.triumers.newsnippetback.domain.aggregate.vo.*;
import org.triumers.newsnippetback.domain.dto.SolvedDTO;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/solved")
public class SolvedController {
    private final SolvedService solvedService;
    private final ModelMapper modelMapper;

    @Autowired
    public SolvedController(SolvedService solvedService, ModelMapper modelMapper) {
        this.solvedService = solvedService;
        this.modelMapper = modelMapper;
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

    /* 설명. 2. 사용자가 과거에 풀었던 문제들 조회 */
    @PostMapping("/find/all")
    public ResponseEntity<List<SolvedQuizListResponse>> findSolvedQuizByUserId(@RequestBody SolvedRequest solvedRequest){
        try {
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

            List<Solved> solvedList = solvedService.findSolvedQuizListByUserId(solvedRequest);
            List<SolvedQuizListResponse> SolvedQuizListResponse = solvedList.stream()
                    .map(dot -> modelMapper.map(dot, SolvedQuizListResponse.class))
                    .collect(Collectors.toList());

            return ResponseEntity.ok().body(SolvedQuizListResponse);
        } catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /* 설명. 3. 사용자가 과거에 풀었던 문제 하나 조회 */
    @PostMapping("/find")
    public ResponseEntity<SolvedQuizResponse> findSolvedQuizByUserIdAndQuizId(@RequestBody SolvedRequest solvedRequest){
        try {
            SolvedDTO solvedDTO = solvedService.findSolvedQuizByUserIdAndQuizId(solvedRequest);
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

    /* 설명. 4. 회원이 지정한 날짜에 맞춘 문제 갯수 조회 */
    @PostMapping("/result")
    public ResponseEntity<List<SolvedResultResponse>> findCorrectQuizByUserIdAndSolvedDate(@RequestBody SolvedResultRequest solvedResultRequest){
        try {
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

            List<Solved> solvedList = solvedService.findCorrectQuizByUserIdAndSolvedDate(solvedResultRequest);
            List<SolvedResultResponse> solvedResultResponse = solvedList.stream()
                    .map(dot -> modelMapper.map(dot, SolvedResultResponse.class))
                    .collect(Collectors.toList());

            return ResponseEntity.ok().body(solvedResultResponse);
        } catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
