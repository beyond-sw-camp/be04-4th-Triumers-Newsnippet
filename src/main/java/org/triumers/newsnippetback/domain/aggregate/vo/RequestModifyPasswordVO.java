package org.triumers.newsnippetback.domain.aggregate.vo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RequestModifyPasswordVO {
    private String oldPassword;
    private String newPassword;
}
