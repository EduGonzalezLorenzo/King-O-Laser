package com.telegame.code.repos;

import com.telegame.code.models.Board;
import com.telegame.code.models.GameMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardRepo extends JpaRepository<Board, Long> {
    Optional<Board> findBoardByGameMatch(GameMatch gameMatch);
}
