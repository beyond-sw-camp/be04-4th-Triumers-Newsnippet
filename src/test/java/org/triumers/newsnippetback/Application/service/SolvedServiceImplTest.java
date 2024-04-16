package org.triumers.newsnippetback.Application.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.triumers.newsnippetback.domain.aggregate.entity.Solved;
import org.triumers.newsnippetback.domain.aggregate.vo.SolvedRequest;
import org.triumers.newsnippetback.domain.aggregate.vo.SolvedResultRequest;
import org.triumers.newsnippetback.Application.dto.SolvedDTO;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class SolvedServiceImplTest {
    @Autowired
    private SolvedService solvedService;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @AfterEach
    void rollback() {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        transactionManager.rollback(status);
    }

    @DisplayName("사용자가 입력한 답과 정답 비교하여 판정")
    @Test
    void findSelectedOptionAndCompareAnswer() {
        // Given
        int userId = 1;
        int quizId = 1;
        SolvedRequest solvedRequest = new SolvedRequest(userId, quizId);

        // When
        SolvedDTO solvedDTO = solvedService.findSelectedOptionAndCompareAnswer(solvedRequest);

        // Then
        Assertions.assertNotNull(solvedDTO);
        Assertions.assertEquals(1,solvedDTO.getUserId());
        Assertions.assertEquals(1,solvedDTO.getQuizId());
        Assertions.assertEquals(true, solvedDTO.isCorrect());
    }

    @DisplayName("사용자가 풀었던 문제 리스트 조회")
    @Test
    void findSolvedQuizListByUserId() {
        // Given
        int userId = 1;
        int quizId = 0;

        // When
        SolvedRequest solvedRequest = new SolvedRequest(userId, quizId);
        List<Solved> solvedList = solvedService.findSolvedQuizListByUserId(solvedRequest);

        // Then
        Assertions.assertNotNull(solvedList);
    }

    @DisplayName("사용자가 풀었던 문제 한 개 조회")
    @Test
    void findSolvedQuizByUserID() {
        // Given
        int userId = 1;
        int quizId = 1;
        SolvedRequest solvedRequest = new SolvedRequest(userId, quizId);

        // When
        SolvedDTO solvedDTO = solvedService.findSolvedQuizByUserIdAndQuizId(solvedRequest);

        // Then
        Assertions.assertNotNull(solvedDTO);
        Assertions.assertEquals(1, solvedDTO.getUserId());
        Assertions.assertEquals(1, solvedDTO.getQuizId());
        Assertions.assertEquals(9, solvedDTO.getCategoryId());
        Assertions.assertEquals("EELS 로봇에 관한 다음 설명 중 옳은 것은 무엇입니까?", solvedDTO.getContent());
        Assertions.assertEquals("EELS 로봇은 지구의 앨버타주에 있는 애서배스카 빙하에서 개발되었습니다.", solvedDTO.getOptionA());
        Assertions.assertEquals("이 로봇은 머리 쪽에 자율주행용 라이다와 카메라를 장착하고 있어서 스스로 움직일 수 있습니다.", solvedDTO.getOptionB());
        Assertions.assertEquals("EELS 로봇의 목표는 타이탄 위성에서의 탐사를 위한 것입니다.", solvedDTO.getOptionC());
        Assertions.assertEquals("이 로봇은 무게가 50kg이며, 액추에이터는 총 24개 달려 있습니다.", solvedDTO.getOptionD());
        Assertions.assertEquals("C",solvedDTO.getAnswer());
        Assertions.assertEquals("캐나다 앨버타주의 애서배스카 빙하에서 출발한 NASA의 로봇 탐사 임무에 대한 내용을 담고 있습니다. 이 임무는 미 항공우주국이 개발 중인 외계 생명체 탐사로봇인 EELS(일스)를 사용하여 토성의 위성 엔셀라두스에 보내는 것이 목표입니다. 이 로봇은 지구의 극한 환경에서도 작동할 수 있는 고성능을 갖추고 있으며, 엔셀라두스의 얼음 아래에 있는 바다에서 생명체를 찾는 임무를 수행할 예정입니다.", solvedDTO.getExplanation());
        Assertions.assertEquals("https://www.ytn.co.kr/_ln/0105_202404012353120871", solvedDTO.getNewsLink());
        Assertions.assertEquals(LocalDate.of(2024, 4, 2), solvedDTO.getDate());
    }

    @DisplayName("회원이 지정한 날짜에 맞춘 문제 조회")
    @Test
    void findCorrectQuizByUserIdAndSolvedDate() {
        // Given
        int userId = 1;
        boolean isCorrect = true;
        LocalDate solvedDate = LocalDate.parse("2024-04-08");
        SolvedResultRequest solvedResultRequest = new SolvedResultRequest(userId, isCorrect, solvedDate);

        // When
        List<Solved> solvedList = solvedService.findCorrectQuizByUserIdAndSolvedDate(solvedResultRequest);

        // Then
        Assertions.assertNotNull(solvedList);
    }
}