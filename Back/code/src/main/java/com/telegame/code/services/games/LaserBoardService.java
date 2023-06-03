package com.telegame.code.services.games;

import com.telegame.code.DTO.games.laserboard.LaserBoardDTO;
import com.telegame.code.DTO.games.laserboard.PieceDTO;
import com.telegame.code.builder.games.laserboard.LaserBoardBuilder;
import com.telegame.code.builder.games.laserboard.PieceBuilder;
import com.telegame.code.exceptions.InputFormException;
import com.telegame.code.exceptions.match.MatchInfoException;
import com.telegame.code.exceptions.match.PieceNotFoundException;
import com.telegame.code.forms.games.LaserBoardMoveForm;
import com.telegame.code.models.Board;
import com.telegame.code.models.GameMatch;
import com.telegame.code.models.Player;
import com.telegame.code.models.games.laserboard.Block;
import com.telegame.code.models.games.laserboard.LaserBoard;
import com.telegame.code.models.games.laserboard.pieces.Bouncer;
import com.telegame.code.models.games.laserboard.pieces.King;
import com.telegame.code.models.games.laserboard.pieces.Piece;
import com.telegame.code.models.games.laserboard.pieces.PieceSide;
import com.telegame.code.repos.BoardRepo;
import com.telegame.code.repos.games.laserboard.PieceRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class LaserBoardService {
    private PieceRepo pieceRepo;
    private BoardRepo boardRepo;

    public String movePiece(LaserBoardMoveForm laserBoardMoveForm, Player player, GameMatch gameMatch, LaserBoard laserBoard) {

        Board.MatchStatus matchStatus = laserBoard.getStatus();
        List<Piece> piecesList = pieceRepo.findByPosYAndPosXAndLaserBoardId(
                laserBoardMoveForm.getCurrentPosY(),
                laserBoardMoveForm.getCurrentPosX(),
                laserBoard.getId());
        if (piecesList.size() == 0) throw new PieceNotFoundException();
        //TODO El siguiente error tiene más implicaciones que hay que contemplar. Si pasara habría que borrar la partida
        if (piecesList.size() != 1) throw new RuntimeException();
        Piece piece = piecesList.get(0);

        if (playerCanMove(matchStatus, piece)) {
            if (laserBoardMoveForm.getRotateTo() == null || laserBoardMoveForm.getRotateTo().equals("")) {
                if (!piece.move(laserBoardMoveForm.getNewPosY(), laserBoardMoveForm.getNewPosX())) {
                    throw new InputFormException();
                }
            } else {
                if (!piece.rotate(laserBoardMoveForm.getRotateTo(), piece)) throw new InputFormException();
            }
        } else throw new InputFormException();

        pieceRepo.save(piece);
        boardRepo.save(laserBoard);

        List<Piece> boardDisposition = pieceRepo.findByLaserBoardId(laserBoard.getId());

        prepareLaser(laserBoard, matchStatus, boardDisposition);

        return "Ok";
    }

    private List<int[]> prepareLaser(LaserBoard laserBoard, Board.MatchStatus matchStatus, List<Piece> piecesList) {
        Piece piece;
        if (matchStatus == Board.MatchStatus.PLAYER_ONE_TURN)
            piecesList = pieceRepo.findByPosYAndPosXAndLaserBoardId(9, 7, laserBoard.getId());
        if (matchStatus == Board.MatchStatus.PLAYER_TWO_TURN)
            piecesList = pieceRepo.findByPosYAndPosXAndLaserBoardId(0, 0, laserBoard.getId());

        piece = piecesList.get(0);
        List<Piece> currentDisposition = pieceRepo.findByLaserBoardId(laserBoard.getId());

        Map<String, Object> laserResult = shootLaser(laserBoard, piece.getRotation(), currentDisposition);

        List<int[]> route = (List<int[]>) laserResult.get("route");

        if (matchStatus == Board.MatchStatus.PLAYER_ONE_TURN) laserBoard.setStatus(Board.MatchStatus.PLAYER_TWO_TURN);
        else laserBoard.setStatus(Board.MatchStatus.PLAYER_ONE_TURN);

        laserBoard.setLastAction(formatRoute(route));

        boardRepo.save(laserBoard);

        return route;
    }

    private String formatRoute(List<int[]> route) {
        StringBuilder formattedRoute = new StringBuilder();
        for (int[] coordinate : route) {
            formattedRoute.append(coordinate[0]).append(",").append(coordinate[1]).append("*");
        }
        return formattedRoute.toString();
    }


    private static boolean playerCanMove(Board.MatchStatus matchStatus, Piece piece) {
        return playerOneCanMove(matchStatus, piece) || playerTwoCanMove(matchStatus, piece);
    }

    private static boolean playerOneCanMove(Board.MatchStatus matchStatus, Piece piece) {
        return matchStatus == Board.MatchStatus.PLAYER_ONE_TURN && piece.getOwner() == Piece.Owner.PLAYER_ONE;
    }

    private static boolean playerTwoCanMove(Board.MatchStatus matchStatus, Piece piece) {
        return (matchStatus == Board.MatchStatus.PLAYER_TWO_TURN && piece.getOwner() == Piece.Owner.PLAYER_TWO);
    }

    public Board generateBoard(GameMatch newGameMatch, String metadata) {
        return LaserBoardBuilder.getKingOLaserBoard(newGameMatch, metadata);
    }

    public void savePieces(LaserBoard board) {
        for (Piece piece : board.getPieceList()) {
            piece.setLaserBoard(board);
            pieceRepo.save(piece);
        }
    }

    public LaserBoardDTO generateLaserBoardDTO(LaserBoard board) {

        return LaserBoardDTO.builder()
                .pieces(generatePieceListDTO(board.getPieceList()))
                .lastAction(board.getLastAction())
                .build();
    }

    private List<PieceDTO> generatePieceListDTO(List<Piece> pieceList) {
        List<PieceDTO> pieceDTOList = new ArrayList<>();
        for (Piece piece : pieceList) {
            pieceDTOList.add(PieceDTO.builder()
                    .x(piece.getPosX())
                    .y(piece.getPosY())
                    .owner(piece.getOwner().name())
                    .rotation(piece.getRotation().name())
                    .type(piece.getClass().getName())
                    .build());
        }
        return pieceDTOList;
    }

    public void deletePiece(int posY, int posX, Long laserBoardId) {
        List<Piece> pieceList = pieceRepo.findByPosYAndPosXAndLaserBoardId(posY, posX, laserBoardId);
        Piece pieceToDelete = pieceList.get(0);
        pieceRepo.delete(pieceToDelete);
    }

    public Map<String, Object> shootLaser(LaserBoard laserBoard, Piece.Direction direction, List<Piece> boardDisposition) {

        Map<String, Object> returnMap = new HashMap<>();

        int[] currentPosition;
        List<int[]> route = new ArrayList<>();

        if (laserBoard.getStatus() == Board.MatchStatus.PLAYER_ONE_TURN) {
            currentPosition = new int[]{9, 7};
        } else {
            currentPosition = new int[]{0, 0};
        }

        Object[][] board = buildBoard(boardDisposition);

        while (currentPosition[0] >= 0 && currentPosition[0] <= 9 &&
                currentPosition[1] >= 0 && currentPosition[1] <= 7) {

            int[] newYX = forward(direction, currentPosition);
            int posY = newYX[0];
            int posX = newYX[1];

            if (board[posY][posX] instanceof Piece) {
                Piece piece = (Piece) board[posY][posX];
                System.out.println("sides: " + piece.getSides());
                PieceSide pieceSide = new Block();

                switch (direction) {
                    case NORTH:
                        pieceSide = piece.getSideInfo(Piece.Direction.SOUTH);
                        break;
                    case EAST:
                        pieceSide = piece.getSideInfo(Piece.Direction.WEST);
                        break;
                    case SOUTH:
                        pieceSide = piece.getSideInfo(Piece.Direction.NORTH);
                        break;
                    case WEST:
                        pieceSide = piece.getSideInfo(Piece.Direction.EAST);
                        break;
                }

                boolean bouncer = (piece instanceof Bouncer);

                Piece.Direction nextDirection = pieceSide.interact(direction, piece.getRotation(), bouncer);

                if (nextDirection == Piece.Direction.STOPPED) {
                    returnMap.put("message", "BLOCK");
                    route.add(newYX);
                    returnMap.put("route", route);
                    return returnMap;
                } else if (nextDirection == Piece.Direction.HIT) {
                    int[] next = forward(direction, currentPosition);
                    piece = (Piece) board[next[0]][next[1]];
                    if (piece instanceof King) {
                        if (piece.getOwner() == Piece.Owner.PLAYER_ONE)
                            laserBoard.setStatus(Board.MatchStatus.PLAYER_TWO_WIN);
                        else laserBoard.setStatus(Board.MatchStatus.PLAYER_ONE_WIN);
                        boardRepo.save(laserBoard);
                    }
                    deletePiece(next[0], next[1], laserBoard.getId());
                    returnMap.put("message", "HIT");
                    route.add(next);
                    returnMap.put("route", route);
                    return returnMap;
                } else {
                    direction = nextDirection;
                    currentPosition = newYX;
                    route.add(newYX);
                }

            } else {
                board[posY][posX] = "  /\\  ";
                currentPosition[0] = posY;
                currentPosition[1] = posX;
                route.add(new int[]{posY, posX});
                if (posY == 0 && direction == Piece.Direction.NORTH || posY == 9 && direction == Piece.Direction.SOUTH) {
                    returnMap.put("message", "OUT");
                    returnMap.put("route", route);
                    return returnMap;
                }
                if (posX == 0 && direction == Piece.Direction.WEST || posX == 7 && direction == Piece.Direction.EAST) {
                    returnMap.put("message", "OUT");
                    returnMap.put("route", route);
                    return returnMap;
                }
            }
        }
        return returnMap;
    }

    private int[] forward(Piece.Direction direction, int[] currentPosition) {
        int posY = currentPosition[0];
        int posX = currentPosition[1];
        switch (direction) {
            case NORTH:
                posY--;
                break;
            case EAST:
                posX++;
                break;
            case SOUTH:
                posY++;
                break;
            case WEST:
                posX--;
        }
        return new int[]{posY, posX};
    }

    public static Object[][] buildBoard(List<Piece> piecesList) {
        Object[][] board = new Object[10][8];
        for (Piece piece : piecesList) {
            piece = PieceBuilder.buildPiece(piece.getClass().toString(), piece.getOwner(), piece.getPosY(), piece.getPosX(), piece.getRotation());
            board[piece.getPosY()][piece.getPosX()] = piece;
        }
        return board;
    }
}
