package com.telegame.code.repos.games;

import com.telegame.code.models.games.kingolaser.pieces.Piece;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PieceRepo extends JpaRepository<Piece, Long> {
}
