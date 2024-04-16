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

import static org.junit.jupiter.api.Assertions.*;

@Transactional
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

    @BeforeEach
    void setUserToken() {
        setUserToContextByUsername();
    }

    @DisplayName("사용자가 정답을 제출한 경우 정답 판정 내리는지 테스트")
    @Test
    void isCorrectIsTrueWhenRightOption() throws UserNotFoundException {
        // Given
        int userId = userRepository.findByEmail(userService.findByToken().getEmail()).getId();
        SolvedRequest solvedRequest = new SolvedRequest(userId, TEST_QUIZ_ID);
        solvedRequest.setSolvedDate(LocalDate.now());
        solvedRequest.setSelectedOption(TEST_RIGHT_OPTION);

        // When
        SolvedDTO solvedDTO = solvedService.findSelectedOptionAndCompareAnswer(solvedRequest);

        // Then
        assertNotNull(solvedDTO);
        assertTrue(solvedDTO.isCorrect());
    }

    @DisplayName("사용자가 오답을 제출한 경우 정답 판정 내리는지 테스트")
    @Test
    void isCorrectIsFalseWhenWrongOption() throws UserNotFoundException {
        // Given
        int userId = userRepository.findByEmail(userService.findByToken().getEmail()).getId();
        SolvedRequest solvedRequest = new SolvedRequest(userId, TEST_QUIZ_ID);
        solvedRequest.setSolvedDate(LocalDate.now());
        solvedRequest.setSelectedOption(TEST_WRONG_OPTION);

        // When
        SolvedDTO solvedDTO = solvedService.findSelectedOptionAndCompareAnswer(solvedRequest);

        // Then
        assertNotNull(solvedDTO);
        assertFalse(solvedDTO.isCorrect());
    }

    @DisplayName("사용자가 풀었던 문제 리스트 조회")
    @Test
    void findSolvedQuizListByUserId() throws UserNotFoundException {
        // Given
        int userId = userRepository.findByEmail(userService.findByToken().getEmail()).getId();

        // When
        SolvedRequest solvedRequest = new SolvedRequest(userId, TEST_QUIZ_ID);
        List<Solved> solvedList = solvedService.findSolvedQuizListByUserId(solvedRequest);

        // Then
        assertNotNull(solvedList);
    }

    @DisplayName("회원이 지정한 날짜에 맞춘 문제 조회")
    @Test
    void findCorrectQuizByUserIdAndSolvedDate() throws UserNotFoundException {
        // Given
        int userId = userRepository.findByEmail(userService.findByToken().getEmail()).getId();
        boolean isCorrect = true;
        LocalDate solvedDate = LocalDate.parse("2024-04-08");
        SolvedResultRequest solvedResultRequest = new SolvedResultRequest(userId, isCorrect, solvedDate);

        // When
        List<Solved> solvedList = solvedService.findCorrectQuizByUserIdAndSolvedDate(solvedResultRequest);

        // Then
        assertNotNull(solvedList);
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