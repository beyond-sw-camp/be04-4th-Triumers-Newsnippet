package org.triumers.newsnippetback.Application.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.triumers.newsnippetback.Application.service.QuizService;
import org.triumers.newsnippetback.domain.aggregate.entity.Quiz;
import org.triumers.newsnippetback.domain.aggregate.vo.QuizRequest;
import org.triumers.newsnippetback.domain.aggregate.vo.QuizResponse;
import org.triumers.newsnippetback.domain.dto.QuizDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    private final QuizService quizService;

    private final ModelMapper modelMapper;

    @Autowired
    public QuizController(QuizService quizService, ModelMapper modelMapper) {
        this.quizService = quizService;
        this.modelMapper = modelMapper;
    }

    /* 1. 해당 날짜에 출제되는 문제의 카테고리, 지문, 선택지, 정답률 조회
    ㄴ 문제는 10개씩, 각 날짜마다 1번-10번까지 번호 부여 */
    @PostMapping("/test")
    public ResponseEntity<QuizResponse> findQuizByDateAndNo(@RequestBody QuizRequest quizRequest) {
        try {
            QuizDTO quizDTO = quizService.findQuizByDateAndNo(quizRequest);

            QuizResponse quizResponse = new QuizResponse();
            quizResponse.setContent(quizDTO.getContent());
            quizResponse.setOptionA(quizDTO.getOptionA());
            quizResponse.setOptionB(quizDTO.getOptionB());
            quizResponse.setOptionC(quizDTO.getOptionC());
            quizResponse.setOptionD(quizDTO.getOptionD());
            quizResponse.setCategoryName(quizDTO.getCategoryName());
            quizResponse.setCorrectRate(quizDTO.getCorrectRate());

            return ResponseEntity.ok().body(quizResponse);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    /* 2. 해당 문제의 정답, 해설, 원본 링크 조회
    ㄴ 문제는 10개씩, 각 날짜마다 1번-10번까지 번호 부여 */
    @PostMapping("/answer")
    public ResponseEntity<QuizResponse> findQuizAnswerByDateAndNo(@RequestBody QuizRequest quizRequest) {
        try {
            QuizDTO quizDTO = quizService.findQuizAnswerByDateAndNo(quizRequest);

            QuizResponse quizResponse = new QuizResponse();
            quizResponse.setAnswer(quizDTO.getAnswer());
            quizResponse.setExplanation(quizDTO.getExplanation());
            quizResponse.setNewsLink(quizDTO.getNewsLink());

            return ResponseEntity.ok().body(quizResponse);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    /* 3. 회원이 입력한 답과 문제의 정답이 같은지 조회한 후 정답 여부와 회원이 입력한 답 저장 */


    /* 4. 문제 id 값 이용해 과거에 풀었던 문제 조회
    ㄴ 문제의 카테고리, 지문, 선택지, 정답, 선택했던 답, 해설, 원본 링크, 날짜, 정답률 조회 */
}
