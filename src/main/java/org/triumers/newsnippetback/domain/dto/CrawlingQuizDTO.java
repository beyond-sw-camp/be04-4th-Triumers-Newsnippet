package org.triumers.newsnippetback.domain.dto;

import jakarta.persistence.Column;
import lombok.Data;
import org.triumers.newsnippetback.domain.aggregate.entity.Category;

import java.time.LocalDate;

@Data
public class CrawlingQuizDTO {
    private int id;

    private String content;

    private String optionA;

    private String optionB;

    private String optionC;

    private String optionD;

    private String answer;

    private String explanation;

    private String newsLink;

    private LocalDate newsDate;

    private Category category;

    private boolean isSelected;
}
