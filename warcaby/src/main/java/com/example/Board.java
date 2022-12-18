package com.example;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class Board 
{
  Tile[][] tiles;
  Pawn[] whitePawns;
  Pawn[] blackPawns;
  Board(int x, int y)
  {
    tiles = Tile.generateTiles(x);
    whitePawns = Pawn.generateWhitePawns(y);
    blackPawns = Pawn.generateBlackPawns(y);
  }  

  //TODO: zmiana na getPositionFromServer
  public void setDefaultPosition()
  {
    whitePawns[0].setIndexes(1,8);
    whitePawns[1].setIndexes(3,8);
    whitePawns[2].setIndexes(5,8);
    whitePawns[3].setIndexes(7,8);
    whitePawns[4].setIndexes(9,8);
    whitePawns[5].setIndexes(0,9);
    whitePawns[6].setIndexes(2,9);
    whitePawns[7].setIndexes(4,9);
    whitePawns[8].setIndexes(6,9);
    whitePawns[9].setIndexes(8,9);

    blackPawns[0].setIndexes(1,0);
    blackPawns[1].setIndexes(3,0);
    blackPawns[2].setIndexes(5,0);
    blackPawns[3].setIndexes(7,0);
    blackPawns[4].setIndexes(9,0);
    blackPawns[5].setIndexes(0,1);
    blackPawns[6].setIndexes(2,1);
    blackPawns[7].setIndexes(4,1);
    blackPawns[8].setIndexes(6,1);
    blackPawns[9].setIndexes(8,1);
  }

  public void addEvents(int player, Scene scene)
  {
    if(player==1)
    {
      for (Pawn pawn : whitePawns) 
      {
        pawn.setOnMouseEntered(new EventHandler<MouseEvent>() {
          public void handle(MouseEvent me) {
              scene.setCursor(Cursor.HAND);
          }
        });
        pawn.setOnMouseExited(new EventHandler<MouseEvent>() {
          public void handle(MouseEvent me) {
              scene.setCursor(Cursor.CROSSHAIR);
          }
        });
      }
    }
    else if(player==2)
    {
      for (Pawn pawn : blackPawns) 
      {
        pawn.setOnMouseEntered(new EventHandler<MouseEvent>() {
          public void handle(MouseEvent me) {
              scene.setCursor(Cursor.HAND);
          }
        });
        pawn.setOnMouseExited(new EventHandler<MouseEvent>() {
          public void handle(MouseEvent me) {
              scene.setCursor(Cursor.CROSSHAIR);
          }
        });
      }
    }
  }

  public void addToScene(GridPane pane)
  {
    for (Tile[] tileRow : tiles) 
    {
      for (Tile tile : tileRow)
      {
        pane.add(tile, tile.getXIndex(), tile.getYIndex());
      }
    }
    for (Pawn pawn : blackPawns) 
    {
      pane.add(pawn, pawn.getXIndex(), pawn.getYIndex());
    }
    for (Pawn pawn : whitePawns) 
    {
      pane.add(pawn, pawn.getXIndex(), pawn.getYIndex());
    }
  }

  
}
