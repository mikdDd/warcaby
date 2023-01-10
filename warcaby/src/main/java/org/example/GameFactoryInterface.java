package org.example;

/**
 * Interfejs factory obiektow klasy Game.
 */
public interface GameFactoryInterface {
  /**Metoda tworzaca gre(obiekt klasy Game) zaleznie od wybranego typu.
   * @param gameType typ gry do stworzenia
   * @return obiekt klasy Game
   */
  Game createGame(String gameType);
}
