package com.example;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Pawn extends Circle
{
  private int xIndex;
  private int yIndex;
  private static int SIZE;

  Pawn(char c)
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

  public static Pawn[] generateWhitePawns(int n)
  {
    Pawn[] arr = new Pawn[n];
    for(int i=0; i<n; i++)
    {
      arr[i] = new Pawn('W');
    }
    return arr;
  }

  public static Pawn[] generateBlackPawns(int n)
  {
    Pawn[] arr = new Pawn[n];
    for(int i=0; i<n; i++)
    {
      arr[i] = new Pawn('B');
    }
    return arr;
  }

  public static void activePawns(Pawn[] pawns, Scene scene)
  {
    for (Pawn pawn : pawns) 
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
