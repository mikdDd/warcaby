package org.example;


import java.util.Arrays;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class BoardFX 
{
  TileFX[][] tiles;
  PawnFX[] whitePawns;
  PawnFX[] blackPawns;
  String message;
  Bridge bridge;
  int size;
  int player;
  int lastClickedX;
  int lastClickedY;

  BoardFX(int size, int pawns, int player)
  {
    this.player = player;
    this.size=size;
    tiles = TileFX.generateTiles(size);
    whitePawns = PawnFX.generateWhitePawns(pawns);
    blackPawns = PawnFX.generateBlackPawns(pawns);
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
  public void disableTiles(String positionString)
  {
    int index=0;
    outerloop:
    for (TileFX[] tileRow : tiles) 
    {
      for (TileFX tile : tileRow) 
      {
        if (index >= positionString.length())
        {
          break outerloop;
        }

        if (tile.getXIndex() == Character.getNumericValue(positionString.charAt(index)) &&
            tile.getYIndex() == Character.getNumericValue(positionString.charAt(index+1)))
        {
          tile.setDisable(true);
          tile.setFill(Color.BROWN);
          index += 2;
        }
      }
    }
    tiles[lastClickedX][lastClickedY].setDisable(true);
    tiles[lastClickedX][lastClickedY].setFill(Color.BROWN);
  }
  public void enableTiles(String positionString)
  {
    int index=0;
    outerloop:
    for (TileFX[] tileRow : tiles) 
    {
      for (TileFX tile : tileRow) 
      {
        if (index >= positionString.length())
        {
          break outerloop;
        }

        if (tile.getXIndex() == Character.getNumericValue(positionString.charAt(index)) &&
            tile.getYIndex() == Character.getNumericValue(positionString.charAt(index+1)))
        {
          tile.setDisable(false);
          tile.setFill(Color.GREEN);  
          index+=2;
        }
      }
    }
    tiles[lastClickedX][lastClickedY].setDisable(false);
    tiles[lastClickedX][lastClickedY].setFill(Color.GREEN);
  }
  
  public void setPosition(String positionString)
  {
    String[] positionArray = positionString.split(":");
    int index = 0;
    for (PawnFX pawn : whitePawns) 
    {
      if(index>=positionArray[0].length())
      {
        whitePawns = Arrays.copyOf(whitePawns, whitePawns.length-1);
        System.out.println("Bialych pionkow: " + whitePawns.length);
      }
      else
      {
        if(positionArray[0].charAt(index)== 'D')
        {
          pawn.setKing(true);
          index++;
        }
        pawn.setIndexes(Character.getNumericValue(positionArray[0].charAt(index)), 
          Character.getNumericValue(positionArray[0].charAt(index+1)));
        index+=2;
      }
    }
    index=0;
    for (PawnFX pawn : blackPawns) 
    {
      if(index>=positionArray[1].length())
      {
        blackPawns = Arrays.copyOf(blackPawns, blackPawns.length-1);
        System.out.println("Czarnych pionkow: " + blackPawns.length);
      }
      else
      {
        if(positionArray[1].charAt(index)== 'D')
        {
          pawn.setKing(true);
          index++;
        }
        pawn.setIndexes(Character.getNumericValue(positionArray[1].charAt(index)), 
          Character.getNumericValue(positionArray[1].charAt(index+1)));
        index+=2;
      }
    }
  }
  //TODO: USUNAC
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
              lastClickedX = pawn.getXIndex();
              lastClickedY = pawn.getYIndex();
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
              lastClickedX = pawn.getXIndex();
              lastClickedY = pawn.getYIndex();
            }
        });
      }
    }
  }

  public void addTilesToScene(GridPane pane)
  {
    if(player == 1)
    {
      for (TileFX[] tileRow : tiles) 
      {
        for (TileFX tile : tileRow)
        {
          pane.add(tile, size-1-tile.getXIndex(), size-1-tile.getYIndex());
        }
      }
    }
    else
    {
      for (TileFX[] tileRow : tiles) 
      {
        for (TileFX tile : tileRow)
        {
          pane.add(tile, tile.getXIndex(), tile.getYIndex());
        }
      }
    }
  }
  public void addPawnsToScene(GridPane pane)
  {
    Platform.runLater(new Runnable()
    {
      public void run()
      {
        pane.getChildren().removeAll(blackPawns);
        pane.getChildren().removeAll(whitePawns);
        if (player == 1)
        {
          for (PawnFX pawn : blackPawns) 
          {
            pane.add(pawn, size-1-pawn.getXIndex(), size-1-pawn.getYIndex());
          }
          for (PawnFX pawn : whitePawns) 
          {
            pane.add(pawn, size-1-pawn.getXIndex(), size-1-pawn.getYIndex());
          }
        }
        else
        {
          for (PawnFX pawn : blackPawns) 
          {
            pane.add(pawn, pawn.getXIndex(), pawn.getYIndex());
          }
          for (PawnFX pawn : whitePawns) 
          {
            pane.add(pawn, pawn.getXIndex(), pawn.getYIndex());
          }
        }  
      }
    });
  }
  public void firstOutput(String pos)
  {
    bridge.send(pos);
  }

  public void secondOutput(String pos)
  {
    bridge.send(pos);
  }
}
