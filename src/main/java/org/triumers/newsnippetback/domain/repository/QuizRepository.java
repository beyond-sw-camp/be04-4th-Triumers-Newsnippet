package org.triumers.newsnippetback.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.triumers.newsnippetback.domain.aggregate.entity.Quiz;

public interface QuizRepository  extends JpaRepository<Quiz, Integer> {
}
