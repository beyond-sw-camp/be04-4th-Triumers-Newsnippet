package org.triumers.newsnippetback.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.triumers.newsnippetback.domain.aggregate.entity.Quiz;

import java.time.LocalDate;
@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {
    Quiz findByDateAndNo(LocalDate date, int no);

    Quiz findAnswerById(int quizId);

    Quiz findCategoryIdAndContentAndOptionAAndOptionBAndOptionCAndOptionDAndAnswerById(int quizId);
}
