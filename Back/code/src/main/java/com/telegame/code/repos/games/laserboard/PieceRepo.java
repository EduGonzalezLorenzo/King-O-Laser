package com.telegame.code.repos.games.laserboard;

import com.telegame.code.models.games.laserboard.pieces.Piece;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PieceRepo extends JpaRepository<Piece, Long> {

    List<Piece> findByPosYAndPosXAndLaserBoardId(int posY, int posX, long boardId);

    List<Piece> findByLaserBoardId(long boardId);
}
