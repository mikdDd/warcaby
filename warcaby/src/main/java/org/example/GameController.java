package org.example;

import java.io.*;
import java.net.Socket;
//wzorzec MVC
public class GameController implements Runnable{

    private Socket firstPlayer;
    private Socket secondPlayer;
    private Game game;
    private String tour;


    private final static int FIRST=1;
    private final static int SECOND=2;
    private static int turn=FIRST;


    public GameController(Socket firstPlayer, Socket secondPlayer, Game game){
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.game = game;
    }
    @Override
    public void run() {

        try{
            //Inicjalizacja pobieranie od socketa dla player1
            InputStream inputF = firstPlayer.getInputStream();
            BufferedReader inF = new BufferedReader(new InputStreamReader(inputF));

            //Inicjalizacja pobieranie od socketa dla player2
            InputStream inputS = secondPlayer.getInputStream();
            BufferedReader inS = new BufferedReader(new InputStreamReader(inputS));

            //Inicjalizacja Wysylania do socketa dla player1
            OutputStream outputF = firstPlayer.getOutputStream();
            PrintWriter outF = new PrintWriter(outputF, true);

            //Inicjalizacja Wysylania do socketa dla player2
            OutputStream outputS = secondPlayer.getOutputStream();
            PrintWriter outS = new PrintWriter(outputS, true);

        //TODO wysylanie info bezpośrednio z gamecontroller
        //INIT
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
            String board = game.boardToString();
            //String board = "0020406080113151719102224262821333537393:0626466686173757779708284868881939597999";
            outF.println(board);
            outS.println(board);
            

            String line;
            int pawnX;
            int pawnY;
            int destX;
            int destY;

            tour = game.whichPlayerTurn();
            do 
          {
            if ("white".equals(tour))
            {
                // Odbieranie od 1. gracza kliknietego pionka
                line = inF.readLine();
                // Wypisywanie na serwerze
                pawnX = Character.getNumericValue(line.charAt(0));
                pawnY = Character.getNumericValue(line.charAt(1));
                System.out.println("Pawn: " + pawnX+""+pawnY);
                // Wysylanie do 1. gracza informacji o dostepnych polach
                outF.println(game.possibleMovesToString(game.getPawn(Character.getNumericValue(line.charAt(0)),Character.getNumericValue(line.charAt(1))))); // musi być posortowana według kolumny a potem wiersza // nieparzysta suma to ciemne pola
                // Odbieranie od 1. gracza kliknietego pola
                line = inF.readLine();
                destX = Character.getNumericValue(line.charAt(0));
                destY = Character.getNumericValue(line.charAt(1));
                game.movePawn(game.getPawn(pawnX,pawnY),destX,destY);
                // Wypisywanie na serwerze
                System.out.println("Tile: " + line);
                // wysyłanie tury 
                
                //TODO wysłanie planszy z wykonanym ruchemString position = game.boardToString();
                String position = game.boardToString();
                outF.println(position);
                outS.println(position);

                tour = game.whichPlayerTurn();

                System.out.println(tour);
                if ("white".equals(tour))
                {
                  outF.println(1);
                  outS.println(1);
                }
                else if ("black".equals(tour))
                {
                  outF.println(2);
                  outS.println(2);
                }
                else if ("whitewon".equals(tour))
                {
                  outF.println(-1);
                  outS.println(-1);
                }
                else if ("blackwon".equals(tour))
                {
                  outF.println(-2);
                  outS.println(-2);
                }
                else if ("draw".equals(tour))
                {
                  outF.println(0);
                  outS.println(0);
                }
            }
             else if ("black".equals(tour))
             {
                //Odbieranie od 2. gracza kliknietego pionka
                line = inS.readLine();
                // Wypisywanie na serwerze
                pawnX = Character.getNumericValue(line.charAt(0));
                pawnY = Character.getNumericValue(line.charAt(1));
                System.out.println("Pawn: " + pawnX+""+pawnY);
                // Wysylanie do 2. gracza informacji o dostepnych polach
                outS.println(game.possibleMovesToString(game.getPawn(Character.getNumericValue(line.charAt(0)),Character.getNumericValue(line.charAt(1))))); // musi być posortowana według kolumny a potem wiersza // nieparzysta suma to ciemne pola
                // Odbieranie od 2. gracza kliknietego pola
                line = inS.readLine();
                destX = Character.getNumericValue(line.charAt(0));
                destY = Character.getNumericValue(line.charAt(1));
                game.movePawn(game.getPawn(pawnX,pawnY),destX,destY);
                // Wypisywanie na serwerze
                System.out.println("Tile: " + line);
                
                //TODO wysłanie planszy z wykonanym ruchem
                String position = game.boardToString();
                outF.println(position);
                outS.println(position);

                // wysyłanie tury | narazie zakladam że zawsze się zmienia | 0 w przypadku zakończenia gry
                tour = game.whichPlayerTurn();

                System.out.println(tour);
                if ("white".equals(tour))
                {
                  outF.println(1);
                  outS.println(1);
                }
                else if ("black".equals(tour))
                {
                  outF.println(2);
                  outS.println(2);
                }
                else if ("whitewon".equals(tour))
                {
                  outF.println(-1);
                  outS.println(-1);
                }
                else if ("blackwon".equals(tour))
                {
                  outF.println(-2);
                  outS.println(-2);
                }
                else if ("draw".equals(tour))
                {
                  outF.println(0);
                  outS.println(0);
                }
              }
              else break;

                
            } while (true);

        } catch (IOException ex) {
            System.err.println("ex");
        }
    }
}

