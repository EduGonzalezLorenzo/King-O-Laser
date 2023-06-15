package com.telegame.code.repos;

import com.telegame.code.models.GameMatch;
import com.telegame.code.models.Player;
import com.telegame.code.models.PlayerPlayMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerPlayMatchRepo extends JpaRepository<PlayerPlayMatch, Long> {
    List<PlayerPlayMatch> findByPlayerEquals(Player player);

    List<PlayerPlayMatch> findByGameMatchEquals(GameMatch gameMatch);

    Optional<PlayerPlayMatch> findByPlayerEqualsAndGameMatchEquals(Player player, GameMatch gameMatch);

    @Query(value = "SELECT game_match_id FROM player_play_match GROUP BY game_match_id HAVING COUNT(*) = 1", nativeQuery = true)
    List<Long> findAvailableGames();
}
