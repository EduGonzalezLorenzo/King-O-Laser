package com.telegame.code.repos;

import com.telegame.code.models.GameMatch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepo extends JpaRepository<GameMatch, Long> {
}
