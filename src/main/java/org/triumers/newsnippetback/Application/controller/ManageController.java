package org.triumers.newsnippetback.Application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.triumers.newsnippetback.Application.service.ManageService;
import org.triumers.newsnippetback.domain.aggregate.entity.Quiz;
import org.triumers.newsnippetback.domain.dto.CrawlingQuizDTO;
import org.triumers.newsnippetback.domain.dto.QuizDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/manage")
public class ManageController {

    private final ManageService manageService;

    @Autowired
    public ManageController(ManageService manageService) {
        this.manageService = manageService;
    }

    @PostMapping("/findCrawlingQuiz")
    public List<CrawlingQuizDTO> findCrawlingQuizList(@RequestBody Map<String, String> params) {
        return manageService.selectCrawlingQuizListByDate(LocalDate.parse(params.get("date"),
                                                                          DateTimeFormatter.ISO_DATE));
    }

    @GetMapping("/findCrawlingQuiz/{id}")
    public CrawlingQuizDTO findCrawlingQuizById(@PathVariable int id){
        return manageService.selectCrawlingQuizByID(id);
    }

    @GetMapping("/addQuiz/{id}")
    public ResponseEntity<Quiz> addQuizInList(@PathVariable int id){
        Quiz savedQuiz = manageService.insertSelectedQuizById(id);

        return ResponseEntity.status(HttpStatus.OK).body(savedQuiz);
    }

    @GetMapping("/findSelectedQuiz")
    public List<QuizDTO> findSelectedQuizList(){
        return manageService.selectQuizListByDate(LocalDate.now().plusDays(1));
    }

    @DeleteMapping("/deleteQuiz/{id}")
    public ResponseEntity<QuizDTO> deleteQuizInList(@PathVariable int id){
        QuizDTO deletedQuiz = manageService.deleteQuizInListById(id);

        return ResponseEntity.status(HttpStatus.OK).body(deletedQuiz);
    }

}
