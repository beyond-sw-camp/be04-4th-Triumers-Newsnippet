package org.triumers.newsnippetGPT.Application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.triumers.newsnippetGPT.common.exception.OpenAIResponseFailException;
import org.triumers.newsnippetGPT.domain.aggregate.entity.Category;
import org.triumers.newsnippetGPT.domain.aggregate.entity.CrawlingQuiz;
import org.triumers.newsnippetGPT.domain.dto.ChatGPTRequestDTO;
import org.triumers.newsnippetGPT.domain.dto.ChatGPTResponseDTO;
import org.triumers.newsnippetGPT.domain.repository.CategoryRepository;
import org.triumers.newsnippetGPT.domain.repository.CrawlingQuizRepository;
import org.triumers.newsnippetGPT.domain.service.AIResponseValidationService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;

@Service
public class OpenAIServiceImpl implements OpenAIService {

    private final Map<String, String> openAIEnv = new HashMap<>();
    private final AIResponseValidationService aiResponseValidationService;
    private final CategoryRepository categoryRepository;
    private final CrawlingQuizRepository crawlingQuizRepository;

    @Autowired
    private RestTemplate template;

    @Autowired
    public OpenAIServiceImpl(Environment env, AIResponseValidationService aiResponseValidationService,
                             CategoryRepository categoryRepository, CrawlingQuizRepository crawlingQuizRepository) {
        this.aiResponseValidationService = aiResponseValidationService;
        this.categoryRepository = categoryRepository;
        this.crawlingQuizRepository = crawlingQuizRepository;
        openAIEnv.put("model", env.getProperty("open-ai.model"));
        openAIEnv.put("url", env.getProperty("open-ai.url"));
    }

    @Override
    public CrawlingQuiz generateQuiz(String prompt, String newsLink, LocalDate newsDate, String categoryName) throws OpenAIResponseFailException {
        String model = openAIEnv.get("model");
        String apiURL = openAIEnv.get("url");

        ChatGPTRequestDTO request = new ChatGPTRequestDTO(model, prompt);
        ChatGPTResponseDTO chatGPTResponse =  template.postForObject(apiURL, request, ChatGPTResponseDTO.class);

        if (chatGPTResponse == null) {
            throw new OpenAIResponseFailException();
        }

        Map<String, String> parsedResponse = parsingAIResponseToQuiz(chatGPTResponse.getChoices().get(0).getMessage().getContent());

        int categoryId = findCategoryId(categoryName);

        CrawlingQuiz quiz = insertContentInQuiz(parsedResponse, newsLink, newsDate, categoryId);

        return crawlingQuizRepository.save(quiz);
    }

    private int findCategoryId(String categoryName) {
        Category category = categoryRepository.findByCategoryName(categoryName);

        if (category != null) {
            return category.getId();
        }

        category = new Category();
        category.setCategoryName(categoryName);
        return categoryRepository.save(category).getId();
    }

    private Map<String, String> parsingAIResponseToQuiz(String aiResponse) throws OpenAIResponseFailException {
        StringTokenizer st = new StringTokenizer(aiResponse, "}");
        Map<String, String> response = new HashMap<>();

        try {
            response.put("explanation", st.nextToken());
            response.put("content", st.nextToken());
            response.put("optionA", st.nextToken());
            response.put("optionB", st.nextToken());
            response.put("optionC", st.nextToken());
            response.put("optionD", st.nextToken());
        } catch (Exception e) {
            throw new OpenAIResponseFailException();
        }

        aiResponseValidationService.aiResponseValidation(response);

        response.put("explanation", extractResponseContent(response.get("explanation")));
        response.put("content", extractResponseContent(response.get("content")));
        response.put("optionA", extractResponseContent(response.get("optionA")));
        response.put("optionB", extractResponseContent(response.get("optionB")));
        response.put("optionC", extractResponseContent(response.get("optionC")));
        response.put("optionD", extractResponseContent(response.get("optionD")));

        return response;
    }

    private String extractResponseContent(String response) throws OpenAIResponseFailException {
        int index = response.indexOf("{");
        if (index != -1) {
            return response.substring(index + 1);
        }
        throw new OpenAIResponseFailException();
    }

    private CrawlingQuiz insertContentInQuiz(Map<String, String> response, String newsLink, LocalDate newsDate,
                                             int categoryId) {
        CrawlingQuiz quiz = new CrawlingQuiz();

        response = mixOption(response);

        quiz.setContent(response.get("content"));
        quiz.setOptionA(response.get("optionA"));
        quiz.setOptionB(response.get("optionB"));
        quiz.setOptionC(response.get("optionC"));
        quiz.setOptionD(response.get("optionD"));
        quiz.setAnswer(response.get("answer"));
        quiz.setExplanation(response.get("explanation"));
        quiz.setNewsLink(newsLink);
        quiz.setNewsDate(newsDate);
        quiz.setCategoryId(categoryId);

        return quiz;
    }

    private Map<String, String> mixOption(Map<String, String> response) {

        response.put("answer", "A");

        Random random = new Random();
        int answerNo = random.nextInt(4) + 1;

        String temp;

        switch (answerNo) {
            case 2:
                temp = response.get("optionA");
                response.put("optionA", response.get("optionB"));
                response.put("optionB", temp);
                response.put("answer", "B");
                break;
            case 3:
                temp = response.get("optionA");
                response.put("optionA", response.get("optionC"));
                response.put("optionC", temp);
                response.put("answer", "C");
            case 4:
                temp = response.get("optionA");
                response.put("optionA", response.get("optionD"));
                response.put("optionD", temp);
                response.put("answer", "D");
        }

        return response;
    }
}
