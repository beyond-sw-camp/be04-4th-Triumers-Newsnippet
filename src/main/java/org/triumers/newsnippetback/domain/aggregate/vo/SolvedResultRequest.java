package org.triumers.newsnippetback.domain.aggregate.vo;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SolvedResultRequest {
    private int userId;
    private boolean isCorrect;
    private LocalDate solvedDate;
}
