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

  PawnFX(char c)
  {
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

  public static void setSize(int size)
  {
    SIZE = size;
  }

  public static PawnFX[] generateWhitePawns(int n)
  {
    PawnFX[] arr = new PawnFX[n];
    for(int i=0; i<n; i++)
    {
      arr[i] = new PawnFX('W');
    }
    return arr;
  }

  public static PawnFX[] generateBlackPawns(int n)
  {
    PawnFX[] arr = new PawnFX[n];
    for(int i=0; i<n; i++)
    {
      arr[i] = new PawnFX('B');
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
