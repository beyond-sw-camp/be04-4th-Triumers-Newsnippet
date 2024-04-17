package org.triumers.newsnippetback.Application.dto;

import lombok.Data;
import org.triumers.newsnippetback.domain.aggregate.entity.Category;

import java.time.LocalDate;

@Data
public class QuizDTO {
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
}
