package org.triumers.newsnippetGPT.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.triumers.newsnippetGPT.domain.aggregate.entity.Category;
import org.triumers.newsnippetGPT.domain.aggregate.entity.CrawlingQuiz;

@Repository
public interface CrawlingQuizRepository extends JpaRepository<CrawlingQuiz, Integer> {

}
