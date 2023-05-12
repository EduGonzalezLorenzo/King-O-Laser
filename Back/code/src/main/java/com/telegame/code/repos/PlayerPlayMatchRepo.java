package com.telegame.code.repos;

import com.telegame.code.models.Player;
import com.telegame.code.models.PlayerPlayMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerPlayMatchRepo extends JpaRepository<PlayerPlayMatch, Long> {
    List<PlayerPlayMatch> findByPlayerEquals(Player player);
}
