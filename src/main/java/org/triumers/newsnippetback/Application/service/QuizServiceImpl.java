package org.triumers.newsnippetback.Application.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.triumers.newsnippetback.domain.repository.CategoryRepository;
import org.triumers.newsnippetback.domain.repository.QuizRepository;

@Service
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public QuizServiceImpl(QuizRepository quizRepository, CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.quizRepository = quizRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    // 1
//    @Override
//    public QuizDTO findQuizByDateAndNo(QuizRequest quizRequest) {
//        LocalDate date = quizRequest.getDate();
//        int no = quizRequest.getNo();
//
//        return quizRepository.findByDateAndNo(date, no)
//                .map(quiz -> {
//                    QuizDTO quizDTO = modelMapper.map(quiz, QuizDTO.class);
//
//                    // 카테고리 ID를 사용하여 카테고리 이름 조회
//                    int categoryId = quiz.getCategoryId();
//                    System.out.println("categoryId = " + categoryId);
//
//                    Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
//                    System.out.println("optionalCategory = " + optionalCategory);
//
//                    Category category = optionalCategory.orElseThrow(() -> new NoSuchElementException("Category not found for id: " + categoryId));
//                    System.out.println("category = " + category);
//
//                    // 정답률 계산
//                    double correctRate = (double) quiz.getCorrectCnt() / quiz.getSolvedCnt() * 100;
//                    System.out.println("correctRate = " + correctRate);
//
//                    // QuizDTO에 카테고리 이름과 정답률 설정
//                    quizDTO.setCategoryName(category.getCategoryName());
//                    quizDTO.setCorrectRate(correctRate);
//
//                    System.out.println("correctRate = " + correctRate);
//
//                    return quizDTO;
//                })
//                .orElseThrow(() -> new NoSuchElementException("Quiz not found for date: " + date + " and no: " + no));
//    }

}
