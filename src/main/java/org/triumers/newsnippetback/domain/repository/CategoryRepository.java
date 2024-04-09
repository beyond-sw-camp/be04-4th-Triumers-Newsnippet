package org.triumers.newsnippetback.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.triumers.newsnippetback.domain.aggregate.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
