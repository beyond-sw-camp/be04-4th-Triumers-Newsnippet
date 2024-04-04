package org.triumers.newsnippetback.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "tbl_crawling_quiz")
@Data
public class CrawlingQuiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "CONTENT", nullable = false)
    private String content;

    @Column(name = "OPTION_A", nullable = false)
    private String optionA;

    @Column(name = "OPTION_B", nullable = false)
    private String optionB;

    @Column(name = "OPTION_C", nullable = false)
    private String optionC;

    @Column(name = "OPTION_D", nullable = false)
    private String optionD;

    @Column(name = "ANSWER", nullable = false)
    private String answer;

    @Column(name = "EXPLANATION")
    private String explanation;

    @Column(name = "NEWS_LINK")
    private String newsLink;

    @Column(name = "NEWS_DATE", nullable = false)
    private LocalDate newsDate;

    @Column(name = "CATEGORY_ID")
    private int categoryId;
}
