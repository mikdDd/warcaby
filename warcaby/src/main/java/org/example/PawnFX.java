package org.example;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class PawnFX extends Circle
{
  private int xIndex;
  private int yIndex;
  private static int SIZE;
  private Boolean king;
  PawnFX(char c)
  {
    this.king = false;
    this.setRadius(SIZE/2);
    if(c == 'B')
    {
      this.setFill(Color.BLACK);
    }
    else if(c == 'W')
    {
      this.setFill(Color.WHITE);
    }
  }

  public int getXIndex()
  {
    return xIndex;
  }
  public int getYIndex()
  {
    return yIndex;
  }
  public void setIndexes(int xCord, int yCord)
  {
    this.xIndex = xCord;
    this.yIndex = yCord;
  }
  public void setKing(boolean bool)
  {
    this.king = bool;
  }
  public static void setSize(int size)
  {
    SIZE = size;
  }

  public static PawnFX[] generateWhitePawns(int n)
  {
    PawnFX[] arr = new PawnFX[n];
    PawnFX pawn;
    for(int i=0; i<n; i++)
    {
      pawn = new PawnFX('W');
      pawn.setDisable(true);
      arr[i] = pawn;
    }
    return arr;
  }

  public static PawnFX[] generateBlackPawns(int n)
  {
    PawnFX[] arr = new PawnFX[n];
    PawnFX pawn;
    for(int i=0; i<n; i++)
    {
      pawn = new PawnFX('B');
      pawn.setDisable(true);
      arr[i] = pawn;
    }
    return arr;
  }

  public static void activePawns(PawnFX[] pawns, Scene scene)
  {
    for (PawnFX pawn : pawns) 
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
    }
  }
}
