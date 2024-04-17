package org.triumers.newsnippetback.domain.aggregate.vo;

import lombok.*;
import org.triumers.newsnippetback.domain.aggregate.entity.Category;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class QuizRequest {
    private int id;

    private LocalDate date;

    private int no;

    private String content;

    private String optionA;

    private String optionB;

    private String optionC;

    private String optionD;

    private String answer;

    private String explanation;

    private String newsLink;

    private int solvedCnt;

    private int correctCnt;

    private Category category;

    private int originQuizId;

    private String categoryName;
    private double correctRate;

    public QuizRequest(LocalDate date, int no) {
        this.date = date;
        this.no = no;
    }
}
