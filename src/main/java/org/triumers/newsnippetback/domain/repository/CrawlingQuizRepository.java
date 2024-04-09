package org.triumers.newsnippetback.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.triumers.newsnippetback.domain.aggregate.entity.CrawlingQuiz;

import java.time.LocalDate;
import java.util.List;

public interface CrawlingQuizRepository extends JpaRepository<CrawlingQuiz, Integer> {
    List<CrawlingQuiz> findByNewsDate(LocalDate date);
}
