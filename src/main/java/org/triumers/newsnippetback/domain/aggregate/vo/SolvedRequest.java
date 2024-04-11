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
    private LocalDate date;
}
