package org.triumers.newsnippetback.Application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.triumers.newsnippetback.domain.aggregate.entity.Category;
import org.triumers.newsnippetback.domain.aggregate.entity.Quiz;
import org.triumers.newsnippetback.domain.dto.QuizDTO;
import org.triumers.newsnippetback.domain.repository.CategoryRepository;
import org.triumers.newsnippetback.domain.repository.QuizRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;

    private final CategoryRepository categoryRepository;

    @Autowired
    public QuizServiceImpl(QuizRepository quizRepository, CategoryRepository categoryRepository) {
        this.quizRepository = quizRepository;
        this.categoryRepository = categoryRepository;
    }


    // 정답률 계산 메서드
    private double calculateCorrectRate(int correctCnt, int solvedCnt) {
        if (solvedCnt == 0) {
            return 0.0;
        }
        return (double) correctCnt / solvedCnt * 100;
    }


    public List<QuizDTO> findAllQuizzesByDate(LocalDate date) {
        List<Quiz> quizzes = quizRepository.findAllByDate(date);
        List<QuizDTO> quizDTOs = quizzes.stream()
                .map(quiz -> {
                    QuizDTO quizDTO = new QuizDTO();
                    quizDTO.setId(quiz.getId());
                    quizDTO.setContent(quiz.getContent());
                    quizDTO.setOptionA(quiz.getOptionA());
                    quizDTO.setOptionB(quiz.getOptionB());
                    quizDTO.setOptionC(quiz.getOptionC());
                    quizDTO.setOptionD(quiz.getOptionD());
                    quizDTO.setCategoryName(getCategoryName(quiz.getCategoryId()));
                    quizDTO.setCorrectRate(calculateCorrectRate(quiz.getCorrectCnt(), quiz.getSolvedCnt()));
                    quizDTO.setAnswer(quiz.getAnswer());
                    quizDTO.setExplanation(quiz.getExplanation());
                    quizDTO.setNewsLink(quiz.getNewsLink());
                    return quizDTO;
                })
                .collect(Collectors.toList());
        return quizDTOs;
    }

    private String getCategoryName(int categoryId) {
        Category category = categoryRepository.findById(categoryId);
        if (category == null) {
            throw new NoSuchElementException("Category not found for id: " + categoryId);
        }
        return category.getCategoryName();
    }




}
