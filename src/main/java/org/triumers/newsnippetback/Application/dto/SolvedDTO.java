package org.triumers.newsnippetback.Application.dto;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
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
    private LocalDate solvedDate;
}
