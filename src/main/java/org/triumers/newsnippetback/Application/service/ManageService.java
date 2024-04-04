package org.triumers.newsnippetback.Application.service;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.triumers.newsnippetback.domain.aggregate.entity.Category;
import org.triumers.newsnippetback.domain.aggregate.entity.CrawlingQuiz;
import org.triumers.newsnippetback.domain.aggregate.entity.Quiz;
import org.triumers.newsnippetback.domain.dto.CrawlingQuizDTO;
import org.triumers.newsnippetback.domain.dto.QuizDTO;
import org.triumers.newsnippetback.domain.repository.CategoryRepository;
import org.triumers.newsnippetback.domain.repository.CrawlingQuizRepository;
import org.triumers.newsnippetback.domain.repository.QuizRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManageService {

    QuizRepository quizRepository;
    CrawlingQuizRepository crawlingQuizRepository;
    CategoryRepository categoryRepository;
    ModelMapper mapper;

    @Autowired
    public ManageService(QuizRepository quizRepository, CrawlingQuizRepository crawlingQuizRepository,
                         CategoryRepository categoryRepository, ModelMapper mapper) {
        this.quizRepository = quizRepository;
        this.crawlingQuizRepository = crawlingQuizRepository;
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    public List<CrawlingQuizDTO> selectCrawlingQuizListByDate(LocalDate date){

        List<CrawlingQuiz> crawlingQuizList = crawlingQuizRepository.findByNewsDate(date);

        List<CrawlingQuizDTO> crawlingQuizDTOList = crawlingQuizList.stream()
                .map(crawlingQuiz -> mapper.map(crawlingQuiz, CrawlingQuizDTO.class))
                .collect(Collectors.toList());

        for (int i = 0; i < crawlingQuizList.size(); i++) {
            Category category = categoryRepository.findById(crawlingQuizList.get(i).getCategoryId())
                                                            .orElseThrow(IllegalAccessError::new);
            crawlingQuizDTOList.get(i).setCategory(category);
        }

        return crawlingQuizDTOList;
    }

    public CrawlingQuizDTO selectCrawlingQuizByID(int id) {
        CrawlingQuiz crawlingQuiz = crawlingQuizRepository.findById(id).orElseThrow(IllegalAccessError::new);
        CrawlingQuizDTO crawlingQuizDTO = mapper.map(crawlingQuiz, CrawlingQuizDTO.class);

        Category category = categoryRepository.findById(crawlingQuiz.getCategoryId())
                                                                    .orElseThrow(IllegalAccessError::new);
        crawlingQuizDTO.setCategory(category);

        return crawlingQuizDTO;
    }

    @Transactional
    public List<Quiz> insertSelectedQuiz(List<CrawlingQuizDTO> crawlingQuizDTOList) {

        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        List<Quiz> quizList = crawlingQuizDTOList.stream()
                .map(crawlingQuizDTO -> mapper.map(crawlingQuizDTO, Quiz.class))
                .collect(Collectors.toList());

        for (int i = 0; i < crawlingQuizDTOList.size(); i++) {
            
            CrawlingQuizDTO seletedQuiz = crawlingQuizDTOList.get(i);
            
            quizList.get(i).setNo(i+1);
            quizList.get(i).setDate(LocalDate.now());
            quizList.get(i).setCategoryId(seletedQuiz.getCategory().getId());
            quizList.get(i).setOriginQuizId(seletedQuiz.getId());
        }
        return quizRepository.saveAll(quizList);
    }
}
