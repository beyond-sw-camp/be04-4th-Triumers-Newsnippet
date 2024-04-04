package org.triumers.newsnippetback.domain.aggregate.vo;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class QuizResponse {

    private String content;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private int solvedCnt;
    private int correctCnt;
    private String categoryName;
    private double correctRate;
}
