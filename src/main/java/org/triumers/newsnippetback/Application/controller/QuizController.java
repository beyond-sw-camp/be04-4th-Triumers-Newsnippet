package org.triumers.newsnippetback.Application.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.triumers.newsnippetback.Application.service.QuizService;

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

//    @PostMapping("/test")
//    public ResponseEntity<QuizResponse> findQuizByDateAndNo(@RequestBody QuizRequest quizRequest) {
//        try {
//            QuizDTO quizDTO = quizService.findQuizByDateAndNo(quizRequest);
//            System.out.println("quizDTO = " + quizDTO);
//            QuizResponse quizResponse = modelMapper.map(quizDTO, QuizResponse.class);
//            System.out.println("quizResponse = " + quizResponse);
//
//            // 정답률 계산 및 설정
//            double correctRate = calculateCorrectRate(quizDTO.getCorrectCnt(), quizDTO.getSolvedCnt());
//            System.out.println("correctRate = " + correctRate);
//            quizResponse.setCorrectRate(correctRate);
//            System.out.println("correctRate = " + correctRate);
//
//            return ResponseEntity.ok().body(quizResponse);
//        } catch (NoSuchElementException e) {
//            return ResponseEntity.notFound().build();
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

    // 정답률 계산 메서드
    private double calculateCorrectRate(int correctCnt, int solvedCnt) {
        if (solvedCnt == 0) {
            return 0.0;
        }
        return (double) correctCnt / solvedCnt * 100;
    }





    /* 2. 해당 문제의 정답, 해설, 원본 링크 조회
    ㄴ 문제는 10개씩, 각 날짜마다 1번-10번까지 번호 부여 */


    /* 3. 회원이 입력한 답과 문제의 정답이 같은지 조회한 후 정답 여부와 회원이 입력한 답 저장 */

    /* 4. 문제 id 값 이용해 과거에 풀었던 문제 조회
    ㄴ 문제의 카테고리, 지문, 선택지, 정답, 선택했던 답, 해설, 원본 링크, 날짜, 정답률 조회 */
}
