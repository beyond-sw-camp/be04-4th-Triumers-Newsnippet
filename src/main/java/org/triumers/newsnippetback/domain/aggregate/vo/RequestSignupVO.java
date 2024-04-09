package org.triumers.newsnippetback.domain.aggregate.vo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RequestSignupVO {
    private String name;
    private String nickname;
    private String email;
    private String password;
}
