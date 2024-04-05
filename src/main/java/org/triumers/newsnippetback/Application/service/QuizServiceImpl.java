package org.triumers.newsnippetback.Application.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.triumers.newsnippetback.domain.aggregate.entity.Category;
import org.triumers.newsnippetback.domain.aggregate.entity.Quiz;
import org.triumers.newsnippetback.domain.aggregate.vo.QuizRequest;
import org.triumers.newsnippetback.domain.dto.QuizDTO;
import org.triumers.newsnippetback.domain.repository.CategoryRepository;
import org.triumers.newsnippetback.domain.repository.QuizRepository;

import java.time.LocalDate;
import java.util.NoSuchElementException;

@Service
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;

    private final CategoryRepository categoryRepository;

    @Autowired
    public QuizServiceImpl(QuizRepository quizRepository, CategoryRepository categoryRepository) {
        this.quizRepository = quizRepository;
        this.categoryRepository = categoryRepository;
    }

    // 1
    @Override
    public QuizDTO findQuizByDateAndNo(QuizRequest quizRequest) {
        LocalDate date = quizRequest.getDate();
        int no = quizRequest.getNo();

        Quiz quiz = quizRepository.findByDateAndNo(date, no);

        if (quiz == null) {
            throw new NoSuchElementException("Quiz not found for date: " + date + " and no: " + no);
        }

        QuizDTO quizDTO = new QuizDTO();
        quizDTO.setContent(quiz.getContent());
        quizDTO.setOptionA(quiz.getOptionA());
        quizDTO.setOptionB(quiz.getOptionB());
        quizDTO.setOptionC(quiz.getOptionC());
        quizDTO.setOptionD(quiz.getOptionD());

        // 카테고리 ID를 사용하여 카테고리 이름 조회
        int categoryId = quiz.getCategoryId();
        Category category = categoryRepository.findById(categoryId);
        if (category == null) {
            throw new NoSuchElementException("Category not found for id: " + categoryId);
        }
        quizDTO.setCategoryName(category.getCategoryName());

        // 정답률 계산
        double correctRate = calculateCorrectRate(quiz.getCorrectCnt(), quiz.getSolvedCnt());
        quizDTO.setCorrectRate(correctRate);

        return quizDTO;
    }

    // 정답률 계산 메서드
    private double calculateCorrectRate(int correctCnt, int solvedCnt) {
        if (solvedCnt == 0) {
            return 0.0;
        }
        return (double) correctCnt / solvedCnt * 100;
    }


    // 2
    @Override
    public QuizDTO findQuizAnswerByDateAndNo(QuizRequest quizRequest) {
        LocalDate date = quizRequest.getDate();
        int no = quizRequest.getNo();

        Quiz quiz = quizRepository.findByDateAndNo(date, no);

        if (quiz == null) {
            throw new NoSuchElementException("Quiz not found for date: " + date + " and no: " + no);
        }

        QuizDTO quizDTO = new QuizDTO();
        quizDTO.setAnswer(quiz.getAnswer());
        quizDTO.setExplanation(quiz.getExplanation());
        quizDTO.setNewsLink(quiz.getNewsLink());

        return quizDTO;
    }




}
