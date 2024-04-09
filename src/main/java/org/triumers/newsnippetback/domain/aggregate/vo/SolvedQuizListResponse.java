package org.triumers.newsnippetback.domain.aggregate.vo;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SolvedQuizListResponse {
    private int userId;
    private int quizId;
    private LocalDate solvedDate;
}
