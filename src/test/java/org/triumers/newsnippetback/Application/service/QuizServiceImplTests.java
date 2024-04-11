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
import org.triumers.newsnippetback.domain.aggregate.vo.QuizRequest;
import org.triumers.newsnippetback.domain.dto.QuizDTO;

import java.time.LocalDate;
import java.util.NoSuchElementException;

@SpringBootTest
public class QuizServiceImplTests {

    @Autowired
    private QuizServiceImpl quizServiceImpl;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @AfterEach
    void rollback() {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        transactionManager.rollback(status);
    }

    @DisplayName("퀴즈 조회 - 첫 번째 문제")
    @Test
    void findQuizByDateAndNo_FirstQuestion() {
        // Given
        LocalDate date = LocalDate.of(2024, 4, 2);
        int no = 1;
        QuizRequest quizRequest = new QuizRequest(date, no);

        // When
        QuizDTO quizDTO = quizServiceImpl.findQuizByDateAndNo(quizRequest);

        // Then
        Assertions.assertNotNull(quizDTO);
        Assertions.assertEquals("EELS 로봇에 관한 다음 설명 중 옳은 것은 무엇입니까?", quizDTO.getContent());
        Assertions.assertEquals("EELS 로봇은 지구의 앨버타주에 있는 애서배스카 빙하에서 개발되었습니다.", quizDTO.getOptionA());
        Assertions.assertEquals("이 로봇은 머리 쪽에 자율주행용 라이다와 카메라를 장착하고 있어서 스스로 움직일 수 있습니다.", quizDTO.getOptionB());
        Assertions.assertEquals("EELS 로봇의 목표는 타이탄 위성에서의 탐사를 위한 것입니다.", quizDTO.getOptionC());
        Assertions.assertEquals("이 로봇은 무게가 50kg이며, 액추에이터는 총 24개 달려 있습니다.", quizDTO.getOptionD());
        Assertions.assertEquals("IT/과학", quizDTO.getCategoryName());
        Assertions.assertEquals(70.0, quizDTO.getCorrectRate());
    }

    @DisplayName("퀴즈 조회 - 두 번째 문제")
    @Test
    void findQuizByDateAndNo_SecondQuestion() {
        // Given
        LocalDate date = LocalDate.of(2024, 4, 2);
        int no = 2;
        QuizRequest quizRequest = new QuizRequest(date, no);

        // When
        QuizDTO quizDTO = quizServiceImpl.findQuizByDateAndNo(quizRequest);

        // Then
        Assertions.assertNotNull(quizDTO);
        Assertions.assertEquals("중국 전자상거래 기업 알리바바가 1시간 이내에 전 세계로 상품을 배송하는 시도에 나선다고 하는데, 이를 위해 협업하는 로켓 개발 스타트업은?", quizDTO.getContent());
        Assertions.assertEquals("스페이스 엑스 (Space X)", quizDTO.getOptionA());
        Assertions.assertEquals("스페이스 에포크 (Space Epoch)", quizDTO.getOptionB());
        Assertions.assertEquals("블루 오리진 (Blue Origin)", quizDTO.getOptionC());
        Assertions.assertEquals("로켓랩 (Rocket Lab)", quizDTO.getOptionD());
        Assertions.assertEquals("IT/과학", quizDTO.getCategoryName());
        Assertions.assertEquals(80.0, quizDTO.getCorrectRate());
    }

    @DisplayName("존재하지 않는 퀴즈 조회")
    @Test
    void findNonExistingQuiz() {
        // Given
        LocalDate date = LocalDate.of(2024, 4, 2);
        int no = 999;
        QuizRequest quizRequest = new QuizRequest(date, no);

        // When & Then
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            quizServiceImpl.findQuizByDateAndNo(quizRequest);
        });
    }

    @DisplayName("존재하지 않는 카테고리의 퀴즈 조회")
    @Test
    void findQuizWithNonExistingCategory() {
        // Given
        LocalDate date = LocalDate.of(2024, 4, 2);
        int no = 3;
        QuizRequest quizRequest = new QuizRequest(date, no);

        // When & Then
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            quizServiceImpl.findQuizByDateAndNo(quizRequest);
        });
    }

//    @DisplayName("퀴즈 정답, 해설, 원본 링크 조회 - 첫 번째 문제")
//    @Test
//    void findQuizAnswerByDateAndNo_FirstQuestion() {
//        // Given
//        LocalDate date = LocalDate.of(2024, 4, 2);
//        int no = 1;
//        QuizRequest quizRequest = new QuizRequest(date, no);
//
//        // When
//        QuizDTO quizDTO = quizServiceImpl.findQuizAnswerByDateAndNo(quizRequest);
//
//        // Then
//        Assertions.assertNotNull(quizDTO);
//        Assertions.assertEquals("C", quizDTO.getAnswer());
//        Assertions.assertEquals("캐나다 앨버타주의 애서배스카 빙하에서 출발한 NASA의 로봇 탐사 임무에 대한 내용을 담고 있습니다. 이 임무는 미 항공우주국이 개발 중인 외계 생명체 탐사로봇인 EELS(일스)를 사용하여 토성의 위성 엔셀라두스에 보내는 것이 목표입니다. 이 로봇은 지구의 극한 환경에서도 작동할 수 있는 고성능을 갖추고 있으며, 엔셀라두스의 얼음 아래에 있는 바다에서 생명체를 찾는 임무를 수행할 예정입니다.", quizDTO.getExplanation());
//        Assertions.assertEquals("https://www.ytn.co.kr/_ln/0105_202404012353120871", quizDTO.getNewsLink());
//    }

//    @DisplayName("퀴즈 정답, 해설, 원본 링크 조회 - 두 번째 문제")
//    @Test
//    void findQuizAnswerByDateAndNo_SecondQuestion() {
//        // Given
//        LocalDate date = LocalDate.of(2024, 4, 2);
//        int no = 2;
//        QuizRequest quizRequest = new QuizRequest(date, no);
//
//        // When
//        QuizDTO quizDTO = quizServiceImpl.findQuizAnswerByDateAndNo(quizRequest);
//
//        // Then
//        Assertions.assertNotNull(quizDTO);
//        Assertions.assertEquals("B", quizDTO.getAnswer());
//        Assertions.assertEquals("알리바바가 전 세계 1시간 이내 배송을 추진하기 위해 협업하는 로켓 개발 스타트업은 스페이스 에포크입니다. 이 소식은 2024년 4월 2일에 보도되었습니다. 이는 알리바바의 전 세계적인 물류 서비스를 더욱 확장하기 위한 시도 중 하나로, 스페이스 에포크의 재사용 로켓 XZY-1을 활용하여 1시간 이내에 상품을 운송할 계획입니다.", quizDTO.getExplanation());
//        Assertions.assertEquals("https://www.ytn.co.kr/_ln/0104_202404021429256706", quizDTO.getNewsLink());
//    }

//    @DisplayName("존재하지 않는 퀴즈의 정답, 해설, 원본 링크 조회")
//    @Test
//    void findAnswerOfNonExistingQuiz() {
//        // Given
//        LocalDate date = LocalDate.of(2024, 4, 2);
//        int no = 999;
//        QuizRequest quizRequest = new QuizRequest(date, no);
//
//        // When & Then
//        Assertions.assertThrows(NoSuchElementException.class, () -> {
//            quizServiceImpl.findQuizAnswerByDateAndNo(quizRequest);
//        });
//    }
}
