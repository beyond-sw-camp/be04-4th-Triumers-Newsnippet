package org.triumers.newsnippetback.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
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

    @Column(name = "USER_ID")
    private int userId;

    @Column(name = "QUIZ_ID")
    private int quizId;

    @Column(name = "SOLVED_DATE")
    private LocalDate solvedDate;
}
