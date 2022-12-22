package org.example;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class BoardFX 
{
  TileFX[][] tiles;
  PawnFX[] whitePawns;
  PawnFX[] blackPawns;
  String message;
  Bridge bridge;
  BoardFX(int x, int y)
  {
    tiles = TileFX.generateTiles(x);
    whitePawns = PawnFX.generateWhitePawns(y);
    blackPawns = PawnFX.generateBlackPawns(y);
  }  
  public void setBridge(Bridge bridge)
  {
    this.bridge = bridge;
  }

  public void disableWhite()
  {
    for (PawnFX pawn : whitePawns) {
      pawn.setDisable(true);
    }
  }
  public void enableWhite()
  {
    for (PawnFX pawn : whitePawns) {
      pawn.setDisable(false);
    }
  }
  public void disableBlack()
  {
    for (PawnFX pawn : blackPawns) {
      pawn.setDisable(true);
    }
  }
  public void enableBlack()
  {
    for (PawnFX pawn : blackPawns) {
      pawn.setDisable(false);
    }
  }
  public void disableTiles()
  {
    for (TileFX[] tileRow : tiles) 
    {
      for (TileFX tile : tileRow) 
      {
        if(tile.getColor() == 'B')
        {
          tile.setDisable(true);
        }
      }
    }
  }
  public void enableTiles()
  {
    for (TileFX[] tileRow : tiles) 
    {
      for (TileFX tile : tileRow) 
      {
        if(tile.getColor() == 'B')
        {
          tile.setDisable(false);
        }
      }
    }
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
    
    for (TileFX[] tileRow : tiles) 
    {
      for (TileFX tile : tileRow)
      {
        if(tile.getColor() == 'B')
        {
          tile.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                scene.setCursor(Cursor.HAND);
            }
          });
          tile.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                scene.setCursor(Cursor.CROSSHAIR);
            }
          });
          tile.setOnMouseClicked(new EventHandler<MouseEvent>()
          {
              public void handle(MouseEvent me) 
              {
                secondOutput(tile.getXIndex() + "" + tile.getYIndex());
              }
          });
        }
      }
    }
    if(player==1)
    {
      for (PawnFX pawn : whitePawns) 
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
        pawn.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            public void handle(MouseEvent me) 
            {
              firstOutput(pawn.getXIndex() + "" + pawn.getYIndex());
            }
        });
      }
    }
    else if(player==2)
    {
      for (PawnFX pawn : blackPawns) 
      {
        pawn.setOnMouseEntered(new EventHandler<MouseEvent>() 
        {
          public void handle(MouseEvent me) 
          {
              scene.setCursor(Cursor.HAND);
          }
        });
        pawn.setOnMouseExited(new EventHandler<MouseEvent>() 
        {
          public void handle(MouseEvent me) 
          {
              scene.setCursor(Cursor.CROSSHAIR);
          }
        });
        pawn.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            public void handle(MouseEvent me) 
            {
              firstOutput(pawn.getXIndex() + "" + pawn.getYIndex());
            }
        });
      }
    }
  }

  public void addToScene(GridPane pane)
  {
    for (TileFX[] tileRow : tiles) 
    {
      for (TileFX tile : tileRow)
      {
        pane.add(tile, tile.getXIndex(), tile.getYIndex());
      }
    }
    for (PawnFX pawn : blackPawns) 
    {
      pane.add(pawn, pawn.getXIndex(), pawn.getYIndex());
    }
    for (PawnFX pawn : whitePawns) 
    {
      pane.add(pawn, pawn.getXIndex(), pawn.getYIndex());
    }
  }

  public void firstOutput(String pos)
  {
    this.disableBlack();
    this.disableWhite();
    this.enableTiles();
    message = "";
    message += pos;
  }

  public void secondOutput(String pos)
  {
    this.disableTiles();
    message += pos;
    bridge.send(message);
  }
}
