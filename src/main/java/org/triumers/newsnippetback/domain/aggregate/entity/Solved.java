package org.triumers.newsnippetback.domain.aggregate.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_solved")
public class Solved {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "IS_CORRECT", nullable = false)
    private boolean isCorrect;

    @Column(name = "SELECTED_OPTION")
    private String selectedOption;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QUIZ_ID")
    private Quiz quiz;
}
