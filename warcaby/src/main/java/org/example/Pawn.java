package org.example;

/**Klasa bierki.
 *
 */
public class Pawn {
  /**Wspolrzedna x pionka.*/
  private int xPosition;
  /**Wspolrzedna y pionka.*/
  private int yPosition;
  /**Czy pionek jest aktywny (nie jest zbity).*/
  private boolean active;
  /**Czy pionek jest damka.*/
  private boolean king;
  /**Kolor pionka.*/
  private final String color;

  /**Konstruktor klasy Pawn.
   *
   * @param x wspolrzedna x pionka
   * @param y wspolrzedna y pionka
   * @param c kolor pionka
   * @throws IllegalArgumentException w przypadku podania ujemnych wspolrzednych lub blednego koloru
   */
  public Pawn(final int x, final int y, final String c) throws IllegalArgumentException {
    if (x < 0 || y < 0 || (!"white".equals(c) && !"black".equals(c))) { throw new IllegalArgumentException(); }
    this.setXPosition(x);
    this.setYPosition(y);
    this.color = c;
    this.active = true;
    this.king = false;
  }

  /**Metoda zmieniajaca wspolrzedne pionka.
   *
   * @param x wspolrzedna x, na ktora sie przemieszczamy
   * @param y wspolrzedna y, na ktora sie przemieszczamy
   * @throws IllegalArgumentException w przypadku proby zmiany na ujemne wspolrzedne
   */
  public void changePosition(final int x, final int y) throws IllegalArgumentException {
    if (x < 0 || y < 0) { throw new IllegalArgumentException(); }
    this.setXPosition(x);
    this.setYPosition(y);
  }

  /**Metoda zmieniajaca status aktywnosci pionka jezeli zostal zbity.
   *
   */
  public void capture() {
    this.setActive(false);
  }

  /**Metoda zmieniajaca status pionka na damke.
   *
   */
  public void setKing() {
    this.setKing(true);
  }

  public int getXPosition() {
    return xPosition;
  }

  public void setXPosition(final int xPosition) {
    this.xPosition = xPosition;
  }

  public int getYPosition() {
    return yPosition;
  }

  public void setYPosition(final int yPosition) {
    this.yPosition = yPosition;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(final boolean active) {
    this.active = active;
  }

  public boolean isKing() {
    return king;
  }

  public void setKing(final boolean king) {
    this.king = king;
  }

  public String getColor() {
    return color;
  }

}
