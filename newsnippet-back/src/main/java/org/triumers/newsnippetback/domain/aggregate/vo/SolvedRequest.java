package org.triumers.newsnippetback.domain.aggregate.vo;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SolvedRequest {
    private int userId;
    private int quizId;
    private LocalDate solvedDate;
    private String selectedOption;
    private boolean isCorrect;

    public SolvedRequest(int userId, int quizId) {
        this.userId = userId;
        this.quizId = quizId;
    }
}
