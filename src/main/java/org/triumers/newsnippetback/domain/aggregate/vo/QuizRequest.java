package org.triumers.newsnippetback.domain.aggregate.vo;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class QuizRequest {
    private LocalDate date;
    private int no;
}
