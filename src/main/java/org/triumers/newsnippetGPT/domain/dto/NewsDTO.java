package org.triumers.newsnippetGPT.domain.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NewsDTO {

    private LocalDate newsDate;
    private String newsLink;
    private String title;
    private String content;
    private String category;
}
