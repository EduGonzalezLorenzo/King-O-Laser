package com.telegame.code.repos;

import com.telegame.code.models.kingolaser.pieces.Piece;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PieceRepo extends JpaRepository<Piece, Long> {
}
