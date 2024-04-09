package org.triumers.newsnippetback.domain.aggregate.vo;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SolvedRequest {
    private int userId;
    private int quizId;
}
