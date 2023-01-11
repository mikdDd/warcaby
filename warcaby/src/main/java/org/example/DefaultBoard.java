package org.example;

/**Klasa implementujaca defaultowa plansze do gry w wacaby.
 *
 */
public class DefaultBoard extends Board {

  /**Konstruktor klasy DefaultBoard.
   * @param xSize szerokosc planszy
   * @param ySize wysokosc planszy
   * @param pawnCount liczba pionkow kazdego z graczy
   * @throws IllegalArgumentException gdy podana liczba pionkow nie miesci sie na planszy
   */
  public DefaultBoard(final int xSize, final int ySize, final int pawnCount) throws IllegalArgumentException {
    if (pawnCount > (xSize * ySize) / 4) { throw new IllegalArgumentException(); }
    this.setXSize(xSize);
    this.setYSize(ySize);
    this.setPawnCount(pawnCount);
    this.setPawnList();
    this.updateFields();
  }

    /**
     * {@inheritDoc}
     */
  @Override
  protected void setPawnList() {
    int whiteCounter = 0;
    int blackCounter = 0;

    for (int i = 0; i < getYSize(); i++) {
      if (whiteCounter == getPawnCount()) { break; }

      for (int k = 0; k < getXSize(); k++) {
        if ((k % 2 == 0 && i % 2 == 1) || (k % 2 == 1 && i % 2 == 0)) {
          whiteCounter++;
          getPawnList().add(new Pawn(k, i, "white"));
        }
      }
    }

    for (int i = getYSize() - 1; i >= 0; i--) {
      if (blackCounter == getPawnCount()) { break; }
      for (int k = getXSize() - 1; k >= 0; k--) {
        if ((k % 2 == 0 && i % 2 == 1) || (k % 2 == 1 && i % 2 == 0)) {
          getPawnList().add(new Pawn(k, i, "black"));
          blackCounter++;
        }
      }
    }
  }

}
