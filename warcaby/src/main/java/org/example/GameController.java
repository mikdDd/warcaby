package org.example;

import java.util.List;

  public interface GameController{
  void movePawn(Pawn pawn, int x, int y);

  String possibleMovesToString(Pawn pawn);
  boolean canCapture(Pawn pawn);
  Pawn getPawn(int x, int y);
  String boardToString();
  int getBoardSize();
    int getPawnCount();
    public String whichPlayerTurn();

}
