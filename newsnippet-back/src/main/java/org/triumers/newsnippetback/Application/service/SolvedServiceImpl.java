package org.triumers.newsnippetback.Application.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.triumers.newsnippetback.domain.aggregate.entity.Category;
import org.triumers.newsnippetback.domain.aggregate.entity.Quiz;
import org.triumers.newsnippetback.domain.aggregate.entity.Solved;
import org.triumers.newsnippetback.domain.aggregate.vo.SolvedRequest;
import org.triumers.newsnippetback.domain.aggregate.vo.SolvedResultRequest;
import org.triumers.newsnippetback.Application.dto.SolvedDTO;
import org.triumers.newsnippetback.domain.repository.CategoryRepository;
import org.triumers.newsnippetback.domain.repository.QuizRepository;
import org.triumers.newsnippetback.domain.repository.SolvedRepository;

import java.time.LocalDate;
import java.util.*;

@Service
public class SolvedServiceImpl implements SolvedService{

    private final AuthService authService;
    private final SolvedRepository solvedRepository;
    private final QuizRepository quizRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SolvedServiceImpl(AuthService authService, SolvedRepository solvedRepository, QuizRepository quizRepository, CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.authService = authService;
        this.solvedRepository = solvedRepository;
        this.quizRepository = quizRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    /* 설명. 사용자가 입력한 답과 정답 비교 후 여부 추가 */
    @Override
    public SolvedDTO findSelectedOptionAndCompareAnswer(SolvedRequest solvedRequest) {

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        int userId = authService.getUserId();
        int quizId = solvedRequest.getQuizId();
        String seletedOption = solvedRequest.getSelectedOption();

        Quiz answer = quizRepository.findAnswerById(quizId);

        SolvedDTO solvedDTO = new SolvedDTO();
        solvedDTO.setUserId(userId);
        solvedDTO.setQuizId(quizId);
        solvedDTO.setSolvedDate(LocalDate.now());
        solvedDTO.setCorrect(Objects.equals(answer.getAnswer(), seletedOption));
        solvedDTO.setSelectedOption(seletedOption);

        Solved solved = modelMapper.map(solvedDTO, Solved.class);
        solvedRepository.save(solved);

        // 사용자 정보 업데이트
        authService.updateSolvedQuiz(solvedDTO.isCorrect());

        if (answer == null || solved == null) {
            throw new NoSuchElementException("Quiz or Selected Option not found for quizId: " + quizId);
        }

        return solvedDTO;
    }

    /* 설명. 사용자의 ID로 풀었던 문제 ID들 불러오기 */
    @Override
    public List<Solved> findSolvedQuizListByUserId(SolvedRequest solvedRequest) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        int userId = solvedRequest.getUserId();
        List<Solved> solvedList = solvedRepository.findSolvedQuizByUserId(userId);
        List<Quiz> quizList = new ArrayList<>();

        for (Solved solved: solvedList) {
            int id = solved.getQuizId();
            quizList.add(quizRepository.findById(id).orElseThrow());    // 추후 response 추가 예정
        }

        return solvedList;
    }

    /* 설명. 사용자의 ID와 문제 ID로 문제 내용 불러오기 */
    @Override
    public SolvedDTO findSolvedQuizByUserIdAndQuizId(SolvedRequest solvedRequest) {
        int userId = authService.getUserId();
        int quizId = solvedRequest.getQuizId();

        Solved solvedQuiz = solvedRepository.findSelectedOptionByUserIdAndQuizId(userId, quizId);
        Quiz answer = quizRepository.findCategoryIdAndContentAndOptionAAndOptionBAndOptionCAndOptionDAndAnswerById(quizId);
        Category category = categoryRepository.findById(answer.getCategoryId());

        if (answer == null || solvedQuiz == null) {
            throw new NoSuchElementException("Quiz or Selected Option not found for quizId: " + quizId);
        }

        SolvedDTO solvedDTO = new SolvedDTO();
        solvedDTO.setUserId(solvedQuiz.getUserId());
        solvedDTO.setQuizId(solvedQuiz.getQuizId());
        solvedDTO.setCategoryId(category.getId());
        solvedDTO.setContent(answer.getContent());
        solvedDTO.setOptionA(answer.getOptionA());
        solvedDTO.setOptionB(answer.getOptionB());
        solvedDTO.setOptionC(answer.getOptionC());
        solvedDTO.setOptionD(answer.getOptionD());
        solvedDTO.setAnswer(answer.getAnswer());
        solvedDTO.setSelectedOption(solvedQuiz.getSelectedOption());
        solvedDTO.setExplanation(answer.getExplanation());
        solvedDTO.setNewsLink(answer.getNewsLink());
        solvedDTO.setDate(answer.getDate());

        return solvedDTO;
    }

    @Override
    public List<Solved> findCorrectQuizByUserIdAndSolvedDate(SolvedResultRequest solvedResultRequest) {
        int userId = authService.getUserId();
        boolean isCorrect = solvedResultRequest.isCorrect();
        LocalDate solvedDate = solvedResultRequest.getSolvedDate();

        List<Solved> solvedList = solvedRepository.findIdByUserIdAndIsCorrectAndSolvedDate(userId, isCorrect, solvedDate);

        return solvedList;
    }

    @Override
    public List<SolvedDTO> findSolvedQuizListByUserIdAndDate(SolvedRequest solvedRequest) {
        int userId = authService.getUserId();
        LocalDate solvedDate = solvedRequest.getSolvedDate();

        List<Solved> solvedList = solvedRepository.findSolvedQuizByUserIdAndSolvedDate(userId, solvedDate);
        List<SolvedDTO> solvedDTOList = new ArrayList<>();

        for (Solved solved: solvedList) {
            int id = solved.getQuizId();
            Quiz quiz = quizRepository.findById(id).orElseThrow();

            SolvedDTO solvedDTO = new SolvedDTO();

            solvedDTO.setUserId(solved.getUserId());
            solvedDTO.setQuizId(quiz.getId());
            solvedDTO.setCategoryId(quiz.getCategoryId());
            solvedDTO.setContent(quiz.getContent());
            solvedDTO.setOptionA(quiz.getOptionA());
            solvedDTO.setOptionB(quiz.getOptionB());
            solvedDTO.setOptionC(quiz.getOptionC());
            solvedDTO.setOptionD(quiz.getOptionD());
            solvedDTO.setAnswer(quiz.getAnswer());
            solvedDTO.setSelectedOption(solved.getSelectedOption());
            solvedDTO.setExplanation(quiz.getExplanation());
            solvedDTO.setNewsLink(quiz.getNewsLink());
            solvedDTO.setDate(quiz.getDate());

            solvedDTOList.add(solvedDTO);
        }

        return solvedDTOList;
    }
}
