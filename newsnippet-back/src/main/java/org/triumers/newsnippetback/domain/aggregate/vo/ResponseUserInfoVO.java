package org.triumers.newsnippetback.domain.aggregate.vo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResponseUserInfoVO {
    private String message;
    private String name;
    private String nickname;
    private String email;
    private String userRole;
    private String userStatus;
    private int solvedCnt;
    private int correctCnt;
    private int rank;
}
