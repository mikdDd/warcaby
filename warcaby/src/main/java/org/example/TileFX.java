package org.example;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Klasa obsługująca pola planszy.
 */
public class TileFX extends Rectangle 
{
  int xIndex;
  int yIndex;
  char color;
  private static float SIZE;

  /**
   * Konstruktor.
   * @param x wspolrzedna x
   * @param y wspolrzedna y
   */
  TileFX(int x, int y)
  {
    this.xIndex = x;
    this.yIndex = y;
    this.setWidth(SIZE);
    this.setHeight(SIZE);

    if((x+y) % 2 == 1 )
    {
      color='B';
      this.setFill(Color.SIENNA);
    }
    else
    {
      color='W';
      this.setFill(Color.BEIGE);
    }
  }  

  /**
   * Getter wspolrzednej x
   * @return wspolrzedna x
   */
  public int getXIndex()
  {
    return xIndex;
  }

  /**
   * Getter wspolrzednej y
   * @return wspolrzedna y
   */
  public int getYIndex()
  {
    return yIndex;
  }

  /**
   * Getter koloru.
   * @return kolor pola
   */
  public char getColor()
  {
    return color;
  }

  /**
   * Setter rozmiaru.
   * @param size rozmiar pola
   */
  public static void setSize(float size)
  {
    SIZE = size;
  }

  /**
   * Generowanie tablicy pol 
   * @param n liczba pol
   * @return tablica pol
   */
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
