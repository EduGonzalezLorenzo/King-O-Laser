package com.telegame.code.repos;

import com.telegame.code.models.GameMatch;
import com.telegame.code.models.Player;
import com.telegame.code.models.PlayerPlayMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.crypto.spec.OAEPParameterSpec;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerPlayMatchRepo extends JpaRepository<PlayerPlayMatch, Long> {
    List<PlayerPlayMatch> findByPlayerEquals(Player player);

    Optional<PlayerPlayMatch> findByPlayerEqualsAndGameMatchEquals(Player player, GameMatch gameMatch);
}
