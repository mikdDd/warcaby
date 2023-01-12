package org.example;

import java.util.ArrayList;
import java.util.List;

/**Klasa abstarkcyjna wykorzystujaca wzorzec TEMPLATE,
 * jest szablonem planszy do gry.
 *
 */
public abstract class Board {
  /**Szerokosc planszy.
   *
   */
  private int xSize;
  /**
   * Wysokosc planszy.
   */
  private int ySize;

  /**
   * Liczba pionkow kazdego z graczy.
   */
  private int pawnCount;
  /**
   * Tablica reprezentujaca pola planszy.
   */
  private Pawn[][] fields = new Pawn[getXSize()][getYSize()];
  /**
   * Lista pionow na planszy.
   */
  private final List<Pawn> pawnList = new ArrayList<>();

  /**Metoda aktualizujaca tablice z plansza.
   *
   */
  public void updateFields() {
    this.fields = new Pawn[getXSize()][getYSize()];
    for (final Pawn pawn : getPawnList()) {
      if (pawn.isActive()) {
        final int pawnX = pawn.getXPosition();
        final int pawnY = pawn.getYPosition();
        getFields()[pawnX][pawnY] = pawn;
      }
    }
  }

  /**Metoda ustawiajaca liste pionow.
   *
   */
  protected abstract void setPawnList();

  public int getXSize() {
    return xSize;
  }

  public void setXSize(final int xSize) {
    this.xSize = xSize;
  }

  public int getYSize() {
    return ySize;
  }

  public void setYSize(final int ySize) {
    this.ySize = ySize;
  }

  public int getPawnCount() {
    return pawnCount;
  }

  public void setPawnCount(final int pawnCount) {
    this.pawnCount = pawnCount;
  }

  public Pawn[][] getFields() {
    return fields.clone();
  }

  public List<Pawn> getPawnList() {
    return pawnList;
  }

}
