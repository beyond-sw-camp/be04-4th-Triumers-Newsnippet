package org.triumers.newsnippetback.domain.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SolvedDTO {
    private int id;
    private boolean isCorrect;
    private String selectedOption;
    private int userId;
    private int quizId;
    private int categoryId;
    private String content;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String answer;
    private String explanation;
    private String newsLink;
    private LocalDate date;

}
