package org.triumers.newsnippetback.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.triumers.newsnippetback.domain.aggregate.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    Boolean existsByNickname(String nickname);

    Boolean existsByEmail(String email);
}
