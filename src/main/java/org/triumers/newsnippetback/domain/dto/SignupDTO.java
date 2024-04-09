package org.triumers.newsnippetback.domain.dto;

import jakarta.persistence.Column;
import lombok.*;
import org.triumers.newsnippetback.domain.aggregate.enums.Provider;
import org.triumers.newsnippetback.domain.aggregate.enums.UserRole;
import org.triumers.newsnippetback.domain.aggregate.enums.UserStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SignupDTO {

    private String name;
    private String nickname;
    private String email;
    private String password;
    private UserRole userRole;
    private Provider provider;
    private String snsId;
    private UserStatus userStatus;
}
