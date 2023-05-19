package com.telegame.code.repos.games;

import com.telegame.code.models.games.laserboard.pieces.Piece;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LaserPieceRepo extends JpaRepository<Piece, Long> {

    List<Piece> findByLaserBoardId(long laserBoardId);
    List<Piece> findByPosYAndPosXAndLaserBoardId(int posy, int posx, Long laserBoardId);

   // "SELECT * FROM piece WHERE posy = ? AND posx = ? AND laser_board_id = ?"

}
