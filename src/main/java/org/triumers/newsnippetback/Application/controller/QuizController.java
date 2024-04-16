package org.triumers.newsnippetback.Application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.triumers.newsnippetback.Application.service.QuizService;
import org.triumers.newsnippetback.domain.aggregate.vo.QuizRequest;
import org.triumers.newsnippetback.domain.aggregate.vo.QuizResponse;
import org.triumers.newsnippetback.Application.dto.QuizDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    /* 해당 날짜의 출제되는 문제의 카테고리, 지문, 선택지, 정답률, 정답, 해설, 원본 링크 조회
    * ㄴ 문제는 10개씩, 각 날짜마다 1번-10번까지 번호 부여 */
    @PostMapping("/test")
    public ResponseEntity<List<QuizResponse>> findQuizByDateAndNo(@RequestBody QuizRequest quizRequest) {
        try {
            LocalDate date = quizRequest.getDate();
            List<QuizDTO> quizDTOs = quizService.findAllQuizzesByDate(date);
            List<QuizResponse> quizResponses = quizDTOs.stream()
                    .map(quizDTO -> {
                        QuizResponse quizResponse = new QuizResponse();
                        quizResponse.setId(quizDTO.getId());
                        quizResponse.setNo(quizDTO.getNo());
                        quizResponse.setContent(quizDTO.getContent());
                        quizResponse.setOptionA(quizDTO.getOptionA());
                        quizResponse.setOptionB(quizDTO.getOptionB());
                        quizResponse.setOptionC(quizDTO.getOptionC());
                        quizResponse.setOptionD(quizDTO.getOptionD());
                        quizResponse.setCategoryName(quizDTO.getCategoryName());
                        quizResponse.setCorrectRate(quizDTO.getCorrectRate());
                        quizResponse.setAnswer(quizDTO.getAnswer());
                        quizResponse.setExplanation(quizDTO.getExplanation());
                        quizResponse.setNewsLink(quizDTO.getNewsLink());
                        return quizResponse;
                    })
                    .collect(Collectors.toList());
            return ResponseEntity.ok().body(quizResponses);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
