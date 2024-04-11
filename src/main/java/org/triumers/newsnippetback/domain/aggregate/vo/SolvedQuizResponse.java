package org.triumers.newsnippetback.domain.aggregate.vo;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SolvedQuizResponse {
    private int userId;
    private int quizId;
    private String selectedOption;
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
