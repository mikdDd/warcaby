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
//TODO rysowanie planszy o dowolnych wymiarach
public class BoardFX 
{
  TileFX[][] tiles;
  PawnFX[] whitePawns;
  PawnFX[] blackPawns;
  String message;
  Bridge bridge;
  GridPane pane;
  int size;
  int player;
  int lastClickedX;
  int lastClickedY;

  BoardFX(int size, int pawns, int player, GridPane pane, Bridge bridge)
  {
    this.player = player;
    this.size=size;
    this.pane = pane;
    this.bridge = bridge;
    tiles = TileFX.generateTiles(size);
    whitePawns = PawnFX.generateWhitePawns(pawns);
    blackPawns = PawnFX.generateBlackPawns(pawns);
    addTilesToScene();
  }  
  public void disableWhite()
  {
    for (PawnFX pawn : whitePawns) 
    {
      pawn.setDisable(true);
    }
  }
  public void enableWhite()
  {
    for (PawnFX pawn : whitePawns) 
    {
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
    int index = 0;
    int length = positionString.length()/2;    
    int x; 
    int y;
    for (int i=0; i<length; i++)
    {
      x = Character.getNumericValue(positionString.charAt(index));
      y = Character.getNumericValue(positionString.charAt(index+1));
      tiles[x][y].setDisable(true);
      tiles[x][y].setFill(Color.SIENNA);
      index+=2;
    }
    tiles[lastClickedX][lastClickedY].setDisable(true);
    tiles[lastClickedX][lastClickedY].setFill(Color.SIENNA);
  }
  public void enableTiles(String positionString)
  {
    int index = 0;
    int length = positionString.length()/2;    
    int x; 
    int y;
    for (int i=0; i<length; i++)
    {
      x = Character.getNumericValue(positionString.charAt(index));
      y = Character.getNumericValue(positionString.charAt(index+1));
      tiles[x][y].setDisable(false);
      tiles[x][y].setFill(Color.GREEN);
      index+=2;
    }
    tiles[lastClickedX][lastClickedY].setDisable(false);
    tiles[lastClickedX][lastClickedY].setFill(Color.GREEN);
  }
  
  public void setPosition(String positionString)
  {
    Platform.runLater(new Runnable() 
    {
    public void run()
    {  
      String[] positionArray = positionString.split(":", -1);
      
      pane.getChildren().removeAll(whitePawns);
      pane.getChildren().removeAll(blackPawns);
      int digits = 0;
      for (char ch : positionArray[0].toCharArray()) 
      {
        if (Character.isDigit(ch))
        {
          digits++;
        }  
      }
      if(digits/2<whitePawns.length)
      {
        whitePawns = Arrays.copyOf(whitePawns, whitePawns.length-1);
      }
      int index = 0;
      for (PawnFX pawn : whitePawns) 
      {
        if(positionArray[0].charAt(index)== 'D')
        {
          pawn.setStroke(Color.GRAY);
          index++;
        }
        else
        {
          pawn.setStroke(Color.WHITE);
        }
        pawn.setIndexes(Character.getNumericValue(positionArray[0].charAt(index)), 
          Character.getNumericValue(positionArray[0].charAt(index+1)));
        index+=2;
      }
      
      
      digits = 0;
      for (char ch : positionArray[1].toCharArray()) 
      {
        if (Character.isDigit(ch))
        {
          digits++;
        }  
      }
      if(digits/2<blackPawns.length)
      {
        blackPawns = Arrays.copyOf(blackPawns, blackPawns.length-1);
      }
      index = 0;
      for (PawnFX pawn : blackPawns) 
      {
        if(positionArray[1].charAt(index)== 'D')
        {
          pawn.setStroke(Color.GRAY);
          index++;
        }
        else
        {
          pawn.setStroke(Color.BLACK);
        }
        pawn.setIndexes(Character.getNumericValue(positionArray[1].charAt(index)), 
          Character.getNumericValue(positionArray[1].charAt(index+1)));
        index+=2;
      }

       addPawnsToScene();
      }
      });
    }
  public void addEvents(Scene scene)
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

  private void addTilesToScene()
  {
    if(player == 1)
    {
      for (TileFX[] tileRow : tiles) 
      {
        for (TileFX tile : tileRow)
        {
          pane.add(tile, size-1-tile.getXIndex(), size-tile.getYIndex());
        }
      }
    }
    else
    {
      for (TileFX[] tileRow : tiles) 
      {
        for (TileFX tile : tileRow)
        {
          pane.add(tile, tile.getXIndex(), tile.getYIndex()+1);
        }
      }
    }
  }
  private void addPawnsToScene()
  {
    if (player == 1)
    {
      for (PawnFX pawn : blackPawns) 
      {
        pane.add(pawn, size-1-pawn.getXIndex(), size-pawn.getYIndex());
      }
      for (PawnFX pawn : whitePawns) 
      {
        pane.add(pawn, size-1-pawn.getXIndex(), size-pawn.getYIndex());
      }
    }
    else
    {
      for (PawnFX pawn : blackPawns) 
      {
        pane.add(pawn, pawn.getXIndex(), pawn.getYIndex()+1);
      }
      for (PawnFX pawn : whitePawns) 
      {
        pane.add(pawn, pawn.getXIndex(), pawn.getYIndex()+1);
      }
    }  
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
