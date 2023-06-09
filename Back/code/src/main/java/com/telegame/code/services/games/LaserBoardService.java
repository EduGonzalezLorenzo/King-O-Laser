package com.telegame.code.services.games;

import com.telegame.code.DTO.games.laserboard.LaserBoardDTO;
import com.telegame.code.DTO.games.laserboard.PieceDTO;
import com.telegame.code.builder.games.laserboard.LaserBoardBuilder;
import com.telegame.code.builder.games.laserboard.PieceBuilder;
import com.telegame.code.exceptions.InputFormException;
import com.telegame.code.exceptions.match.MatchInfoException;
import com.telegame.code.exceptions.match.PieceNotFoundException;
import com.telegame.code.exceptions.player.PlayerNameException;
import com.telegame.code.forms.games.LaserBoardMoveForm;
import com.telegame.code.models.Board;
import com.telegame.code.models.GameMatch;
import com.telegame.code.models.Player;
import com.telegame.code.models.PlayerPlayMatch;
import com.telegame.code.models.games.laserboard.Block;
import com.telegame.code.models.games.laserboard.LaserBoard;
import com.telegame.code.models.games.laserboard.pieces.Bouncer;
import com.telegame.code.models.games.laserboard.pieces.King;
import com.telegame.code.models.games.laserboard.pieces.Piece;
import com.telegame.code.models.games.laserboard.pieces.PieceSide;
import com.telegame.code.repos.BoardRepo;
import com.telegame.code.repos.PlayerPlayMatchRepo;
import com.telegame.code.repos.games.laserboard.PieceRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class LaserBoardService {
    private PieceRepo pieceRepo;
    private BoardRepo boardRepo;
    private PlayerPlayMatchRepo playerPlayMatchRepo;

    public String movePiece(LaserBoardMoveForm laserBoardMoveForm, Player player, GameMatch gameMatch, LaserBoard laserBoard) {
        Board.MatchStatus matchStatus = laserBoard.getStatus();
        List<Piece> piecesList = pieceRepo.findByPosYAndPosXAndLaserBoardId(
                laserBoardMoveForm.getCurrentPosY(),
                laserBoardMoveForm.getCurrentPosX(),
                laserBoard.getId());
        if (piecesList.size() == 0) throw new PieceNotFoundException();
        if (piecesList.size() != 1) throw new MatchInfoException();
        Piece piece = piecesList.get(0);

        if (playerCanMove(matchStatus, piece, player, gameMatch)) {
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

        prepareLaserShoot(laserBoard, matchStatus, boardDisposition);

        return "Ok";
    }

    private List<int[]> prepareLaserShoot(LaserBoard laserBoard, Board.MatchStatus matchStatus, List<Piece> piecesList) {
        Piece piece;
        if (matchStatus == Board.MatchStatus.PLAYER_ONE_TURN)
            piecesList = pieceRepo.findByPosYAndPosXAndLaserBoardId(9, 7, laserBoard.getId());
        if (matchStatus == Board.MatchStatus.PLAYER_TWO_TURN)
            piecesList = pieceRepo.findByPosYAndPosXAndLaserBoardId(0, 0, laserBoard.getId());

        piece = piecesList.get(0);
        List<Piece> currentDisposition = pieceRepo.findByLaserBoardId(laserBoard.getId());

        Map<String, Object> laserResult = shootLaser(laserBoard, piece.getRotation(), currentDisposition);

        List<int[]> route = (List<int[]>) laserResult.get("route");

        if (!gameIsOver(laserBoard)) {
            laserBoard.setLastAction(formatRoute(route));
            boardRepo.save(laserBoard);
        }
        laserBoard.setLastAction(formatRoute(route));
        boardRepo.save(laserBoard);
        return route;
    }

    private boolean gameIsOver(LaserBoard laserBoard) {
        if (laserBoard.getStatus() == Board.MatchStatus.PLAYER_ONE_TURN) {
            laserBoard.setStatus(Board.MatchStatus.PLAYER_TWO_TURN);
            return false;
        }
        if (laserBoard.getStatus() == Board.MatchStatus.PLAYER_TWO_TURN) {
            laserBoard.setStatus(Board.MatchStatus.PLAYER_ONE_TURN);
            return false;
        }
        return laserBoard.getStatus() != Board.MatchStatus.WAITING;
    }

    private String formatRoute(List<int[]> route) {
        StringBuilder formattedRoute = new StringBuilder();
        for (int[] coordinate : route) {
            formattedRoute.append(coordinate[0]).append(",").append(coordinate[1]).append("*");
        }
        return formattedRoute.toString();
    }


    private boolean playerCanMove(Board.MatchStatus matchStatus, Piece piece, Player player, GameMatch gameMatch) {
        Optional<PlayerPlayMatch> optional = playerPlayMatchRepo.findByPlayerEqualsAndGameMatchEquals(player, gameMatch);
        if (optional.isEmpty()) throw new PlayerNameException();
        PlayerPlayMatch playerPlayMatch = optional.get();

        return playerOneCanMove(matchStatus, piece, playerPlayMatch) || playerTwoCanMove(matchStatus, piece, playerPlayMatch);
    }

    private boolean playerOneCanMove(Board.MatchStatus matchStatus, Piece piece, PlayerPlayMatch playerPlayMatch) {
        return matchStatus == Board.MatchStatus.PLAYER_ONE_TURN
                && piece.getOwner() == Piece.Owner.PLAYER_ONE
                && playerPlayMatch.getPosition().equals("P1");
    }

    private boolean playerTwoCanMove(Board.MatchStatus matchStatus, Piece piece, PlayerPlayMatch playerPlayMatch) {
        return (matchStatus == Board.MatchStatus.PLAYER_TWO_TURN
                && piece.getOwner() == Piece.Owner.PLAYER_TWO)
                && playerPlayMatch.getPosition().equals("P2");
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

    public LaserBoardDTO generateLaserBoardDTO(LaserBoard board, PlayerPlayMatch playerPlayMatch) {

        return LaserBoardDTO.builder()
                .matchId(board.getGameMatch().getId())
                .pieces(generatePieceListDTO(board.getPieceList()))
                .lastAction(board.getLastAction())
                .status(board.getStatus().toString())
                .position(playerPlayMatch.getPosition())
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
                route.add(new int[]{posY, posX});
                Piece piece = (Piece) board[posY][posX];
                new Block();
                PieceSide pieceSide = switch (direction) {
                    case NORTH -> piece.getSide(Piece.Direction.SOUTH);
                    case EAST -> piece.getSide(Piece.Direction.WEST);
                    case SOUTH -> piece.getSide(Piece.Direction.NORTH);
                    case WEST -> piece.getSide(Piece.Direction.EAST);
                    default -> new Block();
                };

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
                        endGame(laserBoard, piece);
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

    private void endGame(LaserBoard laserBoard, Piece piece) {
        if (piece.getOwner() == Piece.Owner.PLAYER_ONE) {
            laserBoard.setStatus(Board.MatchStatus.PLAYER_TWO_WIN);
        } else {
            laserBoard.setStatus(Board.MatchStatus.PLAYER_ONE_WIN);
        }
        boardRepo.save(laserBoard);
    }

    private int[] forward(Piece.Direction direction, int[] currentPosition) {
        int posY = currentPosition[0];
        int posX = currentPosition[1];
        switch (direction) {
            case NORTH -> posY--;
            case EAST -> posX++;
            case SOUTH -> posY++;
            case WEST -> posX--;
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
