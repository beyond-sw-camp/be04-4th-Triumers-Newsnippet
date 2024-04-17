package org.triumers.newsnippetback.domain.aggregate.vo;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SolvedResultResponse {
    private int id;
    private boolean isCorrect;
}
