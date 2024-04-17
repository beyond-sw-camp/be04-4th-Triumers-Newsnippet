package org.triumers.newsnippetGPT.Application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.triumers.newsnippetGPT.Application.service.OpenAIService;
import org.triumers.newsnippetGPT.Application.service.PromptService;
import org.triumers.newsnippetGPT.common.exception.NewsContentNullException;
import org.triumers.newsnippetGPT.common.exception.NewsTitleNullException;
import org.triumers.newsnippetGPT.common.exception.OpenAIResponseFailException;
import org.triumers.newsnippetGPT.domain.aggregate.vo.RequestNewsVO;
import org.triumers.newsnippetGPT.domain.aggregate.vo.ResponseMessageVO;
import org.triumers.newsnippetGPT.domain.dto.NewsDTO;

import java.time.LocalDate;
import java.util.StringTokenizer;

@RestController
@RequestMapping("/quiz")
public class CrawlingQuizController {

    private final PromptService promptService;
    private final OpenAIService openAIService;

    @Autowired
    public CrawlingQuizController(PromptService promptService, OpenAIService openAIService) {
        this.promptService = promptService;
        this.openAIService = openAIService;
    }

    @GetMapping("/health_check")
    public ResponseEntity<ResponseMessageVO> healthCheck() {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessageVO("Server is online"));
    }

    @PostMapping("/generate")
    public ResponseEntity<ResponseMessageVO> generateQuiz(@RequestBody RequestNewsVO newsVO) {

        ResponseMessageVO response = new ResponseMessageVO();

        NewsDTO newsDTO = changeNewsVOToNewsDTO(newsVO);
        String prompt;

        try {
            prompt = promptService.createPrompt(newsDTO);
        } catch (NewsTitleNullException | NewsContentNullException e) {
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        try {
            openAIService.generateQuiz(prompt, newsDTO.getNewsLink(), newsDTO.getNewsDate(), newsDTO.getCategory());
        } catch (OpenAIResponseFailException e) {
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessageVO("Quiz Generate Success"));
    }

    private NewsDTO changeNewsVOToNewsDTO(RequestNewsVO newsVO) {
        StringTokenizer st = new StringTokenizer(newsVO.getNewsDate(), "-");
        int year = Integer.parseInt(st.nextToken());
        int month = Integer.parseInt(st.nextToken());
        int dayOfMonth = Integer.parseInt(st.nextToken());

        return new NewsDTO(LocalDate.of(year, month, dayOfMonth), newsVO.getNewsLink(),
                newsVO.getTitle().replace("\"", "\'"),
                newsVO.getContent().replace("\"", "\'"), newsVO.getCategory());
    }
}
