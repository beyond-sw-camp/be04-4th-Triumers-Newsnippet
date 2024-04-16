package org.triumers.newsnippetback.Application.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.triumers.newsnippetback.common.exception.UserNotFoundException;
import org.triumers.newsnippetback.domain.aggregate.entity.Solved;
import org.triumers.newsnippetback.domain.aggregate.entity.User;
import org.triumers.newsnippetback.domain.aggregate.vo.SolvedRequest;
import org.triumers.newsnippetback.domain.aggregate.vo.SolvedResultRequest;
import org.triumers.newsnippetback.Application.dto.SolvedDTO;
import org.triumers.newsnippetback.domain.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SolvedServiceImplTest {

    private final int TEST_QUIZ_ID = 1;
    private final String TEST_RIGHT_OPTION = "C";
    private final String TEST_WRONG_OPTION = "A";

    @Autowired
    private SolvedService solvedService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @BeforeEach
    void setUserToken() {
        setUserToContextByUsername();
    }

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

    @DisplayName("퀴즈 정답 제출시 유저 정보 업데이트 테스트")
    @Test
    @Transactional
    void userInfoUpdateWhenCorrect() throws UserNotFoundException {
        // given
        User user = userRepository.findByEmail(userService.findByToken().getEmail());
        int solvedCnt = user.getSolvedCnt();
        int correctCnt = user.getCorrectCnt();
        SolvedRequest request = new SolvedRequest(user.getId(), TEST_QUIZ_ID);
        request.setSolvedDate(LocalDate.now());
        request.setSelectedOption(TEST_RIGHT_OPTION);

        // when
        solvedService.findSelectedOptionAndCompareAnswer(request);
        user = userRepository.findByEmail(userService.findByToken().getEmail());

        // then
        assertEquals(solvedCnt + 1, user.getSolvedCnt());
        assertEquals(correctCnt + 1, user.getCorrectCnt());
    }

    @DisplayName("퀴즈 오답 제출시 유저 정보 업데이트 테스트")
    @Test
    @Transactional
    void userInfoUpdateWhenWrong() throws UserNotFoundException {
        // given
        User user = userRepository.findByEmail(userService.findByToken().getEmail());
        int solvedCnt = user.getSolvedCnt();
        int correctCnt = user.getCorrectCnt();
        SolvedRequest request = new SolvedRequest(user.getId(), TEST_QUIZ_ID);
        request.setSolvedDate(LocalDate.now());
        request.setSelectedOption(TEST_WRONG_OPTION);

        // when
        solvedService.findSelectedOptionAndCompareAnswer(request);
        user = userRepository.findByEmail(userService.findByToken().getEmail());

        // then
        assertEquals(solvedCnt + 1, user.getSolvedCnt());
        assertEquals(correctCnt, user.getCorrectCnt());
    }

    private void setUserToContextByUsername() {
        CustomUserDetailsService customUserDetailsService = new CustomUserDetailsService(userRepository);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername("test@gmail.com");
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities()));
    }
}