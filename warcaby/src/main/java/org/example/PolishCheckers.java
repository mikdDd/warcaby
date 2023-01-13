package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**Klasa implementujaca warcaby polskie.
 */
public class PolishCheckers extends GameTypeTemplate {

  /**Konstruktor klasy.
   *
   */
  public PolishCheckers() {
    this.xSize = 10;
    this.ySize = 10;
    this.pawnCount = 20;
    this.board = new DefaultBoard(xSize, ySize, pawnCount);
  }

  /**
   * {@inheritDoc}
   */
  @Override
    protected List<Move> checkPawnPossibleMoves(final Pawn pawn) {
    final List<Move> possibleMoves = new ArrayList<>();

    if (this.multipleCapturePawn != null && !pawn.equals(multipleCapturePawn) && this.whichPlayerTurn().equals(pawn.getColor())) {
      return possibleMoves;
    }
    final List<Pawn> pawnWithCapture = this.playerPawnWithCaptureList(pawn.getColor());
    if (!pawnWithCapture.isEmpty() && !pawnWithCapture.contains(pawn)) {
      return possibleMoves;
    }

    final int xPawn = pawn.getXPosition();
    final int yPawn = pawn.getYPosition();
    boolean captureExist = false;

    for (int x = xPawn - 1; x <= xPawn + 1; x += 2) {
      for (int y = yPawn - 1; y <= yPawn + 1; y += 2) {
        int xFlag;
        int yFlag;

        if (x >= 0 && x < 10 && y >= 0 && y < 10) {
          if (board.getFields()[x][y] != null && board.getFields()[x][y].isActive()) {
            if (!Objects.equals(board.getFields()[x][y].getColor(), pawn.getColor())) {      //bicie
              xFlag = coordsRelation(x, y, xPawn, yPawn)[0];
              yFlag = coordsRelation(x, y, xPawn, yPawn)[1];
              if (x + xFlag >= 0 && x + xFlag < 10 && y + yFlag >= 0 && y + yFlag < 10 && xFlag != 0 && yFlag != 0 && board.getFields()[x + xFlag][y + yFlag] == null) {
                possibleMoves.add(new Move(x + xFlag, y + yFlag, true));
                captureExist = true;
              }
            }
          } else {
            if ("white".equals(pawn.getColor())) {
              if (y > yPawn) {
                possibleMoves.add(new Move(x, y, false));
              }
            } else {
              if (y < yPawn) {
                possibleMoves.add(new Move(x, y, false));
              }
            }
          }
        }
      }
    }
    //jezeli wystapilo bicie usuwamy z listy wszystkie ruchy nie będące biciami
    if (captureExist) {
      possibleMoves.removeIf(move -> !move.isCapture());
    }
    return possibleMoves;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Move> checkKingPossibleMoves(final Pawn pawn) {
    final List<Move> possibleMoves = new ArrayList<>();

    if (this.multipleCapturePawn != null && !pawn.equals(multipleCapturePawn) && this.multipleCapturePawn.getColor().equals(pawn.getColor())) {
      return possibleMoves;
    }
    if (!this.playerPawnWithCaptureList(pawn.getColor()).isEmpty() && !this.playerPawnWithCaptureList(pawn.getColor()).contains(pawn)) {
      return possibleMoves;
    }

    boolean captureExist = false;
    final int xPawn = pawn.getXPosition();
    final int yPawn = pawn.getYPosition();

    for (int xIncrement = -1; xIncrement <= 1; xIncrement += 2) {       //iteracja po przekątnych od damki
      for (int yIncrement = -1; yIncrement <= 1; yIncrement += 2) {
        for (int x = xPawn, y = yPawn; x >= 0 && x < 10 && y >= 0 && y < 10; x += xIncrement, y += yIncrement) {
          int xFlag;
          int yFlag;

          if (board.getFields()[x][y] != null && board.getFields()[x][y].isActive()) {                    //czy trafilismy na niepuste pole
            if (!Objects.equals(board.getFields()[x][y].getColor(), pawn.getColor())) {         //jezeli pionek innego koloru to mamy bicie
              xFlag = coordsRelation(x, y, xPawn, yPawn)[0];
              yFlag = coordsRelation(x, y, xPawn, yPawn)[1];

              if (x + xFlag >= 0 && x + xFlag < 10 && y + yFlag >= 0 && y + yFlag < 10 && xFlag != 0 && yFlag != 0 && (board.getFields()[x + xFlag][y + yFlag] == null || !board.getFields()[x + xFlag][y + yFlag].isActive())) {
                possibleMoves.add(new Move(x + xFlag, y + yFlag, true));
                captureExist = true;
              }
              break;
            } else {
              if (x != xPawn && y != yPawn) { break; }                              //jezeli trafilismy na pionek naszego koloru przechodzimy do innej przekatnej
            }
          } else {
            possibleMoves.add(new Move(x, y, false));               //jezeli trafilismy na puste pole, dodajemy je

          }
        }
      }
    }

    if (captureExist) { possibleMoves.removeIf(move -> !move.isCapture()); }
    return possibleMoves;
  }

  /**
   * {@inheritDoc}
   */
  @Override
    public boolean canPawnCapture(final Pawn pawn) {
    final int xPawn = pawn.getXPosition();
    final int yPawn = pawn.getYPosition();
    int xFlag;
    int yFlag;


    for (int x = xPawn - 1; x <= xPawn + 1; x += 2) {
      for (int y = yPawn - 1; y <= yPawn + 1; y += 2) {
        if (x >= 0 && x < 10 && y >= 0 && y < 10 && board.getFields()[x][y] != null  && board.getFields()[x][y].isActive() && !Objects.equals(board.getFields()[x][y].getColor(), pawn.getColor())) {
          xFlag = coordsRelation(x, y, xPawn, yPawn)[0];
          yFlag = coordsRelation(x, y, xPawn, yPawn)[1];

          if (x + xFlag >= 0 && x + xFlag < 10 && y + yFlag >= 0 && y + yFlag < 10 && xFlag != 0 && yFlag != 0 && board.getFields()[x + xFlag][y + yFlag] == null) {
            return true;
          }
        }
      }
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean canKingCapture(final Pawn pawn) {
    final int xPawn = pawn.getXPosition();
    final int yPawn = pawn.getYPosition();
    int xFlag;
    int yFlag;

    for (int xIncrement = -1; xIncrement <= 1; xIncrement += 2) {         //iteracja po przekątnych od damki
      for (int yIncrement = - 1; yIncrement <= 1; yIncrement += 2) {
        for (int x = xPawn, y = yPawn; x >= 0 && x < 10 && y >= 0 && y < 10; x += xIncrement, y += yIncrement) {
          if (board.getFields()[x][y] != null && board.getFields()[x][y].isActive()) {                    //czy trafilismy na niepuste pole
            if (!Objects.equals(board.getFields()[x][y].getColor(), pawn.getColor())) {         //jezeli pionek innego koloru to mamy bicie

              xFlag = coordsRelation(x, y, xPawn, yPawn)[0];
              yFlag = coordsRelation(x, y, xPawn, yPawn)[1];

              if (x + xFlag >= 0 && x + xFlag < 10 && y + yFlag >= 0 && y + yFlag < 10 && xFlag != 0 && yFlag != 0 && (board.getFields()[x + xFlag][y + yFlag] == null || !board.getFields()[x + xFlag][y + yFlag].isActive())) {
                return true;
              }
              break;
            } else {
              if (x != xPawn && y != yPawn) { break; }                              //jezeli trafilismy na pionek naszego koloru przechodzimy do innej przekatnej
            }
          }
        }
      }
    }
    return false;
  }

}
