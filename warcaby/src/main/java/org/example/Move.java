package org.example;

import java.util.Objects;

/**Klasa implementujaca ruch pionka.
 *
 */
public class Move {
  /**
   * Wspolrzedna X na ktora jest ruch.
   */
  private final int x;
  /**
   * Wspolrzedna Y na ktora jest ruch.
   */
  private final int y;
  /**
   * Zmienna przechowujaca informacje czy ruch zawiera bicie.
   */
  private final boolean capture;

  /**Konstruktor klasy Move.
   * @param x wspolrzedna na ktora odbywa sie ruch
   * @param y wspolrzedna na ktora odbywa sie ruch
   * @param capture czy ruch jest biciem
   */
  public Move(final int x, final int y, final boolean capture) {
    this.x = x;
    this.y = y;
    this.capture = capture;
  }

  public boolean isCapture() {
    return this.capture;
  }

  public int getY() {
    return y;
  }

  public int getX() {
    return x;
  }

  @Override
  public boolean equals(final Object obj) {
    return obj instanceof Move && ((Move) obj).getX() == this.x && ((Move) obj).getY() == this.y && ((Move) obj).isCapture() == this.capture;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y, capture);
  }
}
