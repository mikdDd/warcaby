package org.example;

/**Interfejs dla trybow gry, wykorzystywany przez serwer.
 *
 */
public interface Game {
  /**Metoda ruszajaca bierka po planszy.
   * @param pawn bierka
   * @param x wspolrzedna x, na ktora sie ruszamy
   * @param y wspolrzedna y, na ktora sie ruszamy
   */
  void movePawn(Pawn pawn, int x, int y);


  /**Metoda zwracajaca stringa zawierajacego mozliwe ruchy danej bierki.
   * @param pawn bierka ktorej ruchy sprawdzamy
   * @return string zawierajacy wspolrzedne pol na ktore moze ruszyc sie dany pionek
   */
  String possibleMovesToString(Pawn pawn);

  /**Metoda zwracajaca bierke znajdujaca sie na danej pozycji.
   * @param x wspolrzedna x
   * @param y wspolrzedna y
   * @return bierka znajdujaca sie na danych wspolrzednych
   */
  Pawn getPawn(int x, int y);

  /**Metoda zwracaja stringa zawierajacego pozycje bierek obu graczy.
   * podane jako wspolrzedne,
   * najpierw bierki biale, a potem czarne oddzielone ":",
   * przed wspolrzednymi damki wstawiana jest litera "D"
   * @return string zawierajacy wspolrzedne wszystkich aktywnych pionkow na planszy
   */
  String boardToString();

  /**Metoda zwracajaca rozmiar planszy.
   * @return rozmiar planszy
   */
  int getBoardSize();

  /**Metoda zwracajaca liczbe pionkow dla kazdego gracza.
   * @return liczba pionkow
   */
  int getPawnCount();

  /**Metoda zwracaja obecna ture oraz informacje o wygranej.
   * @return aktywna tura lub strone ktora wygrala gre
   */
  String whichPlayerTurn();

}
