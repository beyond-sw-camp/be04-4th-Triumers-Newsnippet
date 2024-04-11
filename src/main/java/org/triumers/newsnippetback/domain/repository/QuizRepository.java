package org.triumers.newsnippetback.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.triumers.newsnippetback.domain.aggregate.entity.Quiz;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {
    Quiz findByDateAndNo(LocalDate date, int no);

    Quiz findAnswerById(int quizId);

    Quiz findCategoryIdAndContentAndOptionAAndOptionBAndOptionCAndOptionDAndAnswerById(int quizId);

    List<Quiz> findByDateOrderByNoAsc(LocalDate date);

    List<Quiz> findByDateAndNoGreaterThanOrderByNoAsc(LocalDate localDate, int no);

    Integer countByDate(LocalDate localDate);

    Integer countByDateAndOriginQuizId(LocalDate localDate, int id);

    Quiz findByOriginQuizIdAndDate(int id, LocalDate date);

    List<Quiz> findAllByDate(LocalDate date);

    List<Quiz> findByDateAndNoIn(LocalDate date, List<Integer> nos);
}
