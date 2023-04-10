package com.telegame.code.repos;

import com.telegame.code.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepo extends JpaRepository<Player, Long> {
    Optional<Player> findByEmailEquals(String email);
    Optional<Player> findByPlayerNameEquals(String playerName);
}
