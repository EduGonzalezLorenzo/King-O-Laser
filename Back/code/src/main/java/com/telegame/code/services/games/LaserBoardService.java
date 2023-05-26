package com.telegame.code.services.games;

import com.telegame.code.DTO.games.laserboard.LaserBoardDTO;
import com.telegame.code.DTO.games.laserboard.PieceDTO;
import com.telegame.code.builder.games.laserboard.LaserBoardBuilder;
import com.telegame.code.builder.games.laserboard.PieceBuilder;
import com.telegame.code.exceptions.match.IllegalMoveException;
import com.telegame.code.exceptions.match.PieceNotFoundException;
import com.telegame.code.forms.games.LaserBoardMoveForm;
import com.telegame.code.models.Board;
import com.telegame.code.models.GameMatch;
import com.telegame.code.models.games.laserboard.LaserBoard;
import com.telegame.code.models.games.laserboard.pieces.Bouncer;
import com.telegame.code.models.games.laserboard.pieces.King;
import com.telegame.code.models.games.laserboard.pieces.Piece;
import com.telegame.code.models.games.laserboard.pieces.PieceSide;
import com.telegame.code.repos.BoardRepo;
import com.telegame.code.repos.games.laserboard.PieceRepo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class LaserBoardService {
    private PieceRepo pieceRepo;
    private BoardRepo boardRepo;

    public String movePiece(LaserBoard laserBoard, LaserBoardMoveForm laserBoardMoveForm, GameMatch gameMatch) {
        Piece piece = getPieceFromForm(laserBoardMoveForm, gameMatch, laserBoard);

        if (playerCanMove(laserBoard.getStatus(), piece)) {
            if (laserBoardMoveForm.getRotateTo() == null || laserBoardMoveForm.getRotateTo().equals("")) {
                if (!piece.move(laserBoardMoveForm.getNewPosY(), laserBoardMoveForm.getNewPosX())) {
                    throw new IllegalMoveException();
                }
            } else {
                if (!piece.rotate(laserBoardMoveForm.getRotateTo(), piece)) throw new IllegalMoveException();
            }
        } else throw new IllegalMoveException();

        prepareLaserShoot(laserBoard);

        return "Ok";
    }

    private void prepareLaserShoot(LaserBoard laserBoard) {
        Piece piece = getPieceFromLaserBoard(laserBoard);
        List<Piece> currentDisposition = pieceRepo.findByLaserBoardId(laserBoard.getId());
        LaserBean laserBean = shootLaser(laserBoard, piece.getRotation(), currentDisposition);

        if (laserBoard.getStatus() == Board.MatchStatus.PLAYER_ONE_TURN)
            laserBoard.setStatus(Board.MatchStatus.PLAYER_TWO_TURN);
        else laserBoard.setStatus(Board.MatchStatus.PLAYER_ONE_TURN);

        laserBoard.setLastAction(formatRoute(laserBean.route));
        pieceRepo.save(piece);
        boardRepo.save(laserBoard);
    }

    public LaserBean shootLaser(LaserBoard laserBoard, Piece.Direction direction, List<Piece> boardDisposition) {
        int[] currentPosition = getStartPoint(laserBoard);
        List<int[]> route = new ArrayList<>();
        route.add(getStartPoint(laserBoard));
        Object[][] board = buildBoard(boardDisposition);

        while (positionInBoard(currentPosition)) {
            int[] newYX = forward(direction, currentPosition);
            int posY = newYX[0];
            int posX = newYX[1];

            if (board[posY][posX] instanceof Piece piece) {
                PieceSide pieceSide = getPieceSide(direction, piece);
                boolean bouncer = (piece instanceof Bouncer);
                Piece.Direction nextDirection = pieceSide.interact(direction, piece.getRotation(), bouncer);

                if (nextDirection == Piece.Direction.STOPPED) {
                    route.add(newYX);
                    return LaserBean.builder()
                            .message("BLOCK")
                            .route(route)
                            .build();
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
                    route.add(next);
                    return LaserBean.builder()
                            .route(route)
                            .message("HIT").build();
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
                if ((posY == 0 && direction == Piece.Direction.NORTH || posY == 9 && direction == Piece.Direction.SOUTH) ||
                        (posX == 0 && direction == Piece.Direction.WEST || posX == 7 && direction == Piece.Direction.EAST)) {
                    return LaserBean.builder()
                            .route(route)
                            .message("OUT").build();
                }
            }
        }
        return LaserBean.builder()
                .route(route)
                .message("OUT").build();
    }

    private PieceSide getPieceSide(Piece.Direction direction, Piece piece) {
        return switch (direction) {
            case NORTH -> piece.getSideInfo(Piece.Direction.SOUTH);
            case EAST -> piece.getSideInfo(Piece.Direction.WEST);
            case SOUTH -> piece.getSideInfo(Piece.Direction.NORTH);
            case WEST -> piece.getSideInfo(Piece.Direction.EAST);
            default -> throw new PieceNotFoundException();
        };
    }

    private int[] getStartPoint(LaserBoard laserBoard) {
        if (laserBoard.getStatus() == Board.MatchStatus.PLAYER_ONE_TURN) return new int[]{9, 7};
        else return new int[]{0, 0};
    }

    private boolean positionInBoard(int[] currentPosition) {
        return currentPosition[0] >= 0 && currentPosition[0] <= 9 &&
                currentPosition[1] >= 0 && currentPosition[1] <= 7;
    }

    private Piece getPieceFromLaserBoard(LaserBoard laserBoard) {
        List<Piece> piecesList = laserBoard.getPieceList();
        if (laserBoard.getStatus() == Board.MatchStatus.PLAYER_ONE_TURN)
            return getPieceFromCoordinates(7, 9, piecesList);
        else
            return getPieceFromCoordinates(0, 0, piecesList);
    }

    private Piece getPieceFromCoordinates(int x, int y, List<Piece> pieceList) {
        for (Piece piece : pieceList) {
            if (piece.getPosX() == x && piece.getPosY() == y) return piece;
        }
        throw new PieceNotFoundException();
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
                    .type(piece.getType())
                    .build());
        }
        return pieceDTOList;
    }

    public void deletePiece(int posY, int posX, Long laserBoardId) {
        List<Piece> pieceList = pieceRepo.findByPosYAndPosXAndLaserBoardId(posY, posX, laserBoardId);
        Piece pieceToDelete = pieceList.get(0);
        pieceRepo.delete(pieceToDelete);
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

    private Piece getPieceFromForm(LaserBoardMoveForm laserBoardMoveForm, GameMatch gameMatch, LaserBoard laserBoard) {
        List<Piece> piecesList = pieceRepo.findByPosYAndPosXAndLaserBoardId(
                laserBoardMoveForm.getCurrentPosY(),
                laserBoardMoveForm.getCurrentPosX(),
                laserBoard.getId());
        if (piecesList.size() == 0) throw new PieceNotFoundException();
        //La siguiente linea genera un bucle infinito en el que matchService tiene un LaserBoardService y
        //LaserBoardService tiene un matchService, provocando esto un error de compilación.
        //Ser no es estrictamente necesario borrar la partida, ya que sobre el papel este caso no se debería dar nunca
        //pero precisamente por eso si se diera la partida se consideraría corrupta al habar varias piezas en la misma casiila
        //y habria que borrarla.Hay que decidir que hacer, si pasar o si buscar una manera con una clase nueva para evitar el bucle.
        //if (piecesList.size() != 1) matchService.deleteGameMatch(gameMatch);
        return piecesList.get(0);
    }

    @Builder
    public static class LaserBean {
        List<int[]> route;
        String message;
    }
}
