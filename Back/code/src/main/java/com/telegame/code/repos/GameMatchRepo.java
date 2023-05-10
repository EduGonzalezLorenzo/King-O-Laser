package com.telegame.code.repos;

import com.telegame.code.models.GameMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameMatchRepo extends JpaRepository<GameMatch, Long> {
}
