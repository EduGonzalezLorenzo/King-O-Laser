package com.telegame.code.repos;

import com.telegame.code.models.kingolaser.pieces.Piece;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PieceRepo extends JpaRepository<Piece, Long> {

    List<Piece> findByLaserBoardId(long laserBoardId);
    List<Piece> findByPosYAndPosXAndLaserBoardId(int posy, int posx, Long laserBoardId);

   // "SELECT * FROM piece WHERE posy = ? AND posx = ? AND laser_board_id = ?"

}
