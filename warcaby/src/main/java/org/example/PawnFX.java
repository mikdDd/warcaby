package org.example;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

public class PawnFX extends Circle
{
  private int xIndex;
  private int yIndex;
  private static float SIZE;

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
    this.xIndex = 0;
    this.yIndex = 0;
    this.setStrokeType(StrokeType.INSIDE);
    this.setStrokeWidth(SIZE/6);
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
  public static void setSize(float size)
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
