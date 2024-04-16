package org.triumers.newsnippetback.Application.dto;

import lombok.*;
import org.triumers.newsnippetback.domain.aggregate.enums.Provider;
import org.triumers.newsnippetback.domain.aggregate.enums.UserRole;
import org.triumers.newsnippetback.domain.aggregate.enums.UserStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {

    private String name;
    private String nickname;
    private String email;
    private UserRole userRole;
    private UserStatus userStatus;
    private int solvedCnt;
    private int correctCnt;
    private int rank;
}
