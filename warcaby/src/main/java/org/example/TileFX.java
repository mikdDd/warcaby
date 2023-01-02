package org.example;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TileFX extends Rectangle 
{
  int xIndex;
  int yIndex;
  PawnFX pawn;
  char color;
  private static float SIZE;
  TileFX(int x, int y)
  {
    this.xIndex = x;
    this.yIndex = y;
    this.setWidth(SIZE);
    this.setHeight(SIZE);

    if((x+y) % 2 == 1 )
    {
      color='B';
      this.setFill(Color.BROWN);
    }
    else
    {
      color='W';
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
  public char getColor()
  {
    return color;
  }
  public static void setSize(float size)
  {
    SIZE = size;
    System.out.println(size);
  }

  public static TileFX[][] generateTiles(int n)
  {
    TileFX[][] arr = new TileFX[n][n];
    TileFX tile;
    for (int i=0; i<n; i++)
    {
      for (int j=0; j<n; j++)
      {
        tile = new TileFX(i, j);
        tile.setDisable(true);
        arr[i][j] = tile;
      }
    }
    return arr;
  }
}
