package org.triumers.newsnippetback.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.triumers.newsnippetback.domain.aggregate.entity.Solved;

@Repository
public interface SolvedRepository extends JpaRepository<Solved, Integer> {
    Solved findSelectedOptionByUserIdAndQuizId(int userId, int quizId);
}
