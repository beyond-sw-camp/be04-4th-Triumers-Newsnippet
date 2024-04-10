package org.triumers.newsnippetback.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.triumers.newsnippetback.domain.aggregate.entity.Quiz;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface QuizRepository  extends JpaRepository<Quiz, Integer> {
    List<Quiz> findByDateOrderByNoAsc(LocalDate date);

    List<Quiz> findByDateAndNoGreaterThanOrderByNoAsc(LocalDate localDate, int no);

    Integer countByDate(LocalDate localDate);

    Integer countByDateAndOriginQuizId(LocalDate localDate, int id);

    Quiz findByOriginQuizIdAndDate(int id, LocalDate date);
}
