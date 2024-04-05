package org.triumers.newsnippetGPT.domain.aggregate.vo;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
public class RequestNewsVO {
    private String newsDate;
    private String newsLink;
    private String title;
    private String content;
    private String category;
}
