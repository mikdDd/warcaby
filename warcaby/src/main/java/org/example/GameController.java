package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**Klasa kontrolera wykorzystujaca wzorzec MVC laczaca widok z modelem.*/
public class GameController implements Runnable {
  private final transient  Socket firstPlayer;
  private final transient  Socket secondPlayer;
  private final transient  Game game;


  /**Konstruktor klasy.
   * @param firstPlayer Socket pierwszego gracza
   * @param secondPlayer Socket drugiego gracza
   * @param game interfejs gry
   */
  public GameController(final Socket firstPlayer, final Socket secondPlayer, final Game game) {
    this.firstPlayer = firstPlayer;
    this.secondPlayer = secondPlayer;
    this.game = game;
  }

  @Override
  public void run() {
    try {
      //Inicjalizacja pobierania od socketa dla player1
      final InputStream inputF = firstPlayer.getInputStream();
      final BufferedReader inF = new BufferedReader(new InputStreamReader(inputF));

      //Inicjalizacja pobieranie od socketa dla player2
      final InputStream inputS = secondPlayer.getInputStream();
      final BufferedReader inS = new BufferedReader(new InputStreamReader(inputS));

      //Inicjalizacja Wysylania do socketa dla player1
      final OutputStream outputF = firstPlayer.getOutputStream();
      final PrintWriter outF = new PrintWriter(outputF, true);

      //Inicjalizacja Wysylania do socketa dla player2
      final OutputStream outputS = secondPlayer.getOutputStream();
      final PrintWriter outS = new PrintWriter(outputS, true);


      //numer gracza
      outF.println("1");
      outS.println("2");
      //rozmiar planszy
      outF.println(game.getBoardSize());
      outS.println(game.getBoardSize());

      //ilosc pionkow
      outF.println(game.getPawnCount());
      outS.println(game.getPawnCount());

      //ustawienie pionkow
      final String board = game.boardToString();
      outF.println(board);
      outS.println(board);
            
      String line;
      int pawnX;
      int pawnY;
      int destX;
      int destY;

      String tour = game.whichPlayerTurn();
      while (true) {
        if ("white".equals(tour)) {
          // Odbieranie od 1. gracza kliknietego pionka
          line = inF.readLine();

          // Wypisywanie na serwerze
          pawnX = Character.getNumericValue(line.charAt(0));
          pawnY = Character.getNumericValue(line.charAt(1));
          System.out.println("Pawn: " + pawnX +  pawnY);

          // Wysylanie do 1. gracza informacji o dostepnych polach
          outF.println(game.possibleMovesToString(game.getPawn(Character.getNumericValue(line.charAt(0)), Character.getNumericValue(line.charAt(1))))); // musi być posortowana według kolumny a potem wiersza // nieparzysta suma to ciemne pola

          // Odbieranie od 1. gracza kliknietego pola
          line = inF.readLine();
          destX = Character.getNumericValue(line.charAt(0));
          destY = Character.getNumericValue(line.charAt(1));
          game.movePawn(game.getPawn(pawnX, pawnY), destX, destY);

          // Wypisywanie na serwerze
          System.out.println("Tile: " + line);

          // wysyłanie tury
          final String position = game.boardToString();
          outF.println(position);
          outS.println(position);
          tour = game.whichPlayerTurn();

          System.out.println(tour);
          if ("white".equals(tour)) {
            outF.println(1);
            outS.println(1);
          } else if ("black".equals(tour)) {
            outF.println(2);
            outS.println(2);
          } else if ("whitewon".equals(tour)) {
            outF.println(-1);
            outS.println(-1);
          } else if ("blackwon".equals(tour)) {
            outF.println(-2);
            outS.println(-2);
          } else if ("draw".equals(tour)) {
            outF.println(0);
            outS.println(0);
          }
        } else if ("black".equals(tour)) {
          //Odbieranie od 2. gracza kliknietego pionka
          line = inS.readLine();

          // Wypisywanie na serwerze
          pawnX = Character.getNumericValue(line.charAt(0));
          pawnY = Character.getNumericValue(line.charAt(1));
          System.out.println("Pawn: " + pawnX + pawnY);

          // Wysylanie do 2. gracza informacji o dostepnych polach
          outS.println(game.possibleMovesToString(game.getPawn(Character.getNumericValue(line.charAt(0)), Character.getNumericValue(line.charAt(1))))); // musi być posortowana według kolumny a potem wiersza // nieparzysta suma to ciemne pola

          // Odbieranie od 2. gracza kliknietego pola
          line = inS.readLine();
          destX = Character.getNumericValue(line.charAt(0));
          destY = Character.getNumericValue(line.charAt(1));
          game.movePawn(game.getPawn(pawnX, pawnY), destX, destY);

          // Wypisywanie na serwerze
          System.out.println("Tile: " + line);


          final String position = game.boardToString();
          outF.println(position);
          outS.println(position);

          // wysyłanie tury
          tour = game.whichPlayerTurn();

          System.out.println(tour);
          if ("white".equals(tour)) {
            outF.println(1);
            outS.println(1);
          } else if ("black".equals(tour)) {
            outF.println(2);
            outS.println(2);
          } else if ("whitewon".equals(tour)) {
            outF.println(-1);
            outS.println(-1);
          } else if ("blackwon".equals(tour)) {
            outF.println(-2);
            outS.println(-2);
          } else if ("draw".equals(tour)) {
            outF.println(0);
            outS.println(0);
          }
        } else { break; }
      }
    } catch (IOException ex) {
      System.err.println("ex");
    }
  }
}

