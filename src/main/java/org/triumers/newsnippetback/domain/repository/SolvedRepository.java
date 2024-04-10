package org.triumers.newsnippetback.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.triumers.newsnippetback.domain.aggregate.entity.Solved;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SolvedRepository extends JpaRepository<Solved, Integer> {
    Solved findSelectedOptionByUserIdAndQuizId(int userId, int quizId);

    List<Solved> findIdByUserIdAndIsCorrectAndSolvedDate(int userId, boolean isCorrect, LocalDate solvedDate);

    List<Solved> findSolvedQuizByUserId(int userId);
}
