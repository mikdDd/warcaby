package org.example;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

/**
 * Klasa obslugujaca pionki.
 */
public class PawnFX extends Circle
{
  private int xIndex;
  private int yIndex;
  private static float SIZE;

  /**
   * Konstruktor.
   * @param c kolor ('W'/'B')
   */
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

  /**
   * Getter wspolrzednej x;
   * @return wspolrzedna x
   */
  public int getXIndex()
  {
    return xIndex;
  }

  /**
   * Getter wspolrzednej y;
   * @return wspolrzedna y
   */
  public int getYIndex()
  {
    return yIndex;
  }

  /**
   * Setter wspolrzednych.
   * @param xCord wspolrzedna x
   * @param yCord wspolrzedna y
   */
  public void setIndexes(int xCord, int yCord)
  {
    this.xIndex = xCord;
    this.yIndex = yCord;
  }

  /**
   * Setter rozmiaru.
   * @param size rozmiar
   */
  public static void setSize(float size)
  {
    SIZE = size;
  }

  /**
   * Generowanie tablicy bialych pionkow.
   * @param n liczba pionkow
   * @return tablica pionkow
   */
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

  /**
   * Generowanie tablicy czarnych pionkow.
   * @param n liczba pionkow
   * @return tablica pionkow
   */
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

  /**
   * Aktywacja pionkow.
   * @param pawns tablica pionkow
   * @param scene scena
   */
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