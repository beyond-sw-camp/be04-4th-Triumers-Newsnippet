package org.triumers.newsnippetback.domain.aggregate.entity;

import jakarta.persistence.*;
import org.triumers.newsnippetback.domain.aggregate.enums.Provider;
import org.triumers.newsnippetback.domain.aggregate.enums.UserRole;
import org.triumers.newsnippetback.domain.aggregate.enums.UserStatus;

@Entity
@Table(name = "tbl_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "NICKNAME", nullable = false, unique = true)
    private String nickname;

    @Column(name = "EMAIL", unique = true)
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "SOLVED_CNT", nullable = false)
    private int solvedCnt;

    @Column(name = "CORRECT_CNT", nullable = false)
    private int correctCnt;

    @Column(name = "USER_ROLE", nullable = false)
    private UserRole userRole;

    @Column(name = "PROVIDER", nullable = false)
    private Provider provider;

    @Column(name = "SNS_ID", unique = true)
    private String snsId;

    @Column(name = "USER_STATUS", nullable = false)
    private UserStatus userStatus;

    @Column(name = "LOGIN_FAILED_CNT", nullable = false)
    private int loginFailedCnt;
}
