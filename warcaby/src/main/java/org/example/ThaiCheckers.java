package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**Klasa implementujaca warcaby tajskie.
 *
 */
public class ThaiCheckers extends GameType {

  /**Konstruktor klasy.
   *
   */
  public ThaiCheckers() {
    this.xSize = 8;
    this.ySize = 8;
    this.pawnCount = 8;
    this.board = new DefaultBoard(xSize, ySize, pawnCount);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected List<Move> checkPawnPossibleMoves(final Pawn pawn) {
    final List<Move> possibleMoves = new ArrayList<>();

    if (this.multipleCapturePawn != null && !pawn.equals(multipleCapturePawn) && this.whichPlayerTurn().equals(pawn.color)) {
      return possibleMoves;
    }

    final List<Pawn> pawnWithCapture = this.playerPawnWithCaptureList(pawn.color);
    if (!pawnWithCapture.isEmpty() && !pawnWithCapture.contains(pawn)) {
      return possibleMoves;
    }

    boolean captureExist = false;
    final int xPawn = pawn.xPosition;
    final int yPawn = pawn.yPosition;

    for (int x = xPawn - 1; x <= xPawn + 1; x += 2) {
      int xFlag;
      int yFlag;

      int y;
      if ("white".equals(pawn.color)) {
        y = yPawn + 1;
      } else {
        y = yPawn - 1;
      }

      if (x >= 0 && x < this.xSize && y >= 0 && y < this.ySize) {
        if (board.fields[x][y] != null &&  board.fields[x][y].isActive) {
          if (!Objects.equals(board.fields[x][y].color, pawn.color)) {      //bicie
            xFlag = coordsRelation(x, y, xPawn, yPawn)[0];
            yFlag = coordsRelation(x, y, xPawn, yPawn)[1];

            if (x + xFlag >= 0 && x + xFlag < this.xSize && y + yFlag >= 0 && y + yFlag < this.ySize && xFlag != 0 && yFlag != 0 && board.fields[x + xFlag][y + yFlag] == null) {
              possibleMoves.add(new Move(x + xFlag, y + yFlag, true));
              captureExist = true;
            }
          }
        } else {
          possibleMoves.add(new Move(x, y, false));
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
  protected List<Move> checkKingPossibleMoves(final Pawn pawn) {
    final List<Move> possibleMoves = new ArrayList<>();
    if (this.multipleCapturePawn != null && !pawn.equals(multipleCapturePawn) && this.multipleCapturePawn.color.equals(pawn.color)) {
      return possibleMoves;
    }
    if (!this.playerPawnWithCaptureList(pawn.color).isEmpty() && !this.playerPawnWithCaptureList(pawn.color).contains(pawn)) {
      return possibleMoves;
    }

    final int xPawn = pawn.xPosition;
    final int yPawn = pawn.yPosition;
    boolean captureExist = false;

    for (int xIncrement = - 1; xIncrement <= 1; xIncrement += 2) {         //iteracja po przekątnych od damki
      for (int yIncrement = - 1; yIncrement <= 1; yIncrement += 2) {
        int xFlag;
        int yFlag;
        for (int x = xPawn, y = yPawn; x >= 0 && x < this.xSize && y >= 0 && y < this.ySize; x += xIncrement, y += yIncrement) {
          if (board.fields[x][y] != null && board.fields[x][y].isActive) {                    //czy trafilismy na niepuste pole
            if (!Objects.equals(board.fields[x][y].color, pawn.color)) {         //jezeli pionek innego koloru to mamy bicie
              xFlag = coordsRelation(x, y, xPawn, yPawn)[0];
              yFlag = coordsRelation(x, y, xPawn, yPawn)[1];

              if (x + xFlag >= 0 && x + xFlag < this.xSize && y + yFlag >= 0 && y + yFlag < this.ySize && xFlag != 0 && yFlag != 0 && (board.fields[x + xFlag][y + yFlag] == null || !board.fields[x + xFlag][y + yFlag].isActive)) {
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

    if (captureExist) {
      possibleMoves.removeIf(move -> !move.isCapture());
    }
    return possibleMoves;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean canPawnCapture(final Pawn pawn) {
    int xFlag;
    int yFlag;
    final int xPawn = pawn.xPosition;
    final int yPawn = pawn.yPosition;

    for (int x = xPawn - 1; x <= xPawn + 1; x += 2) {
      int y;
      if ("white".equals(pawn.color)) {
        y = yPawn + 1;
      } else {
        y = yPawn - 1;
      }

      if (x >= 0 && x < xSize && y >= 0 && y < ySize && board.fields[x][y] != null &&  board.fields[x][y].isActive && !Objects.equals(board.fields[x][y].color, pawn.color)) {
        xFlag = coordsRelation(x, y, xPawn, yPawn)[0];
        yFlag = coordsRelation(x, y, xPawn, yPawn)[1];

        if (x + xFlag >= 0 && x + xFlag < this.xSize && y + yFlag >= 0 && y + yFlag < this.ySize && xFlag != 0 && yFlag != 0 && board.fields[x + xFlag][y + yFlag] == null) {
          return true;
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
    final int xPawn = pawn.xPosition;
    final int yPawn = pawn.yPosition;
    int xFlag;
    int yFlag;

    for (int xIncrement = - 1; xIncrement <= 1; xIncrement += 2) {       //iteracja po przekątnych od damki
      for (int yIncrement = - 1; yIncrement <= 1; yIncrement += 2) {
        for (int x = xPawn, y = yPawn; x >= 0 && x < this.xSize && y >= 0 && y < this.ySize; x += xIncrement, y += yIncrement) {
          if (board.fields[x][y] != null && board.fields[x][y].isActive) {                    //czy trafilismy na niepuste pole
            if (!Objects.equals(board.fields[x][y].color, pawn.color)) {         //jezeli pionek innego koloru to mamy bicie
              xFlag = coordsRelation(x, y, xPawn, yPawn)[0];
              yFlag = coordsRelation(x, y, xPawn, yPawn)[1];

              if (x + xFlag >= 0 && x + xFlag < this.xSize && y + yFlag >= 0 && y + yFlag < this.ySize && xFlag != 0 && yFlag != 0 && (board.fields[x + xFlag][y + yFlag] == null || !board.fields[x + xFlag][y + yFlag].isActive)) {
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
