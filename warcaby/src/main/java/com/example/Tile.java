package com.example;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle 
{
  int xIndex;
  int yIndex;
  Pawn pawn;
  private static int SIZE;
  Tile(int x, int y)
  {
    this.xIndex = x;
    this.yIndex = y;
    this.setWidth(SIZE);
    this.setHeight(SIZE);

    if((x+y) % 2 == 1 )
    {
      this.setFill(Color.BROWN);
    }
    else
    {
      this.setFill(Color.YELLOW);
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
  public static void setSize(int size)
  {
    SIZE = size;
  }

  public static Tile[][] generateTiles(int n)
  {
    Tile[][] arr = new Tile[n][n];

    for (int i=0; i<n; i++)
    {
      for (int j=0; j<n; j++)
      {
        arr[i][j] = new Tile(i, j);
      }
    }

    return arr;
  }
}
