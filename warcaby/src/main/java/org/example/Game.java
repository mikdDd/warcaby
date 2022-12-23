package org.example;

import java.io.*;
import java.net.Socket;

public class Game implements Runnable{

    private Socket firstPlayer;
    private Socket secondPlayer;
    private GameController gameController;


    private final static int FIRST=1;
    private final static int SECOND=2;
    private static int turn=FIRST;


    public Game(Socket firstPlayer, Socket secondPlayer, GameController gameController){
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.gameController = gameController;
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
            outF.println(gameController.getBoardSize());
            outS.println(gameController.getBoardSize());
            //ilosc pionkow

            outF.println(gameController.getPawnCount());
            outS.println(gameController.getPawnCount());

            //ustawienie pionkow
            String board = gameController.boardToString();
            //String board = "0020406080113151719102224262821333537393:0626466686173757779708284868881939597999";
            outF.println(board);
            outS.println(board);
            

            String line;
            int pawnX;
            int pawnY;
            int destX;
            int destY;
          do 
          {
            if (gameController.whichPlayerTurn().equals("white"))
            {
                // Odbieranie od 1. gracza kliknietego pionka
                line = inF.readLine();
                // Wypisywanie na serwerze
                pawnX = Character.getNumericValue(line.charAt(0));
                pawnY = Character.getNumericValue(line.charAt(1));
                System.out.println("Pawn: " + pawnX+""+pawnY);
                // Wysylanie do 1. gracza informacji o dostepnych polach
                outF.println(gameController.possibleMovesToString(gameController.getPawn(Character.getNumericValue(line.charAt(0)),Character.getNumericValue(line.charAt(1))))); // musi być posortowana według kolumny a potem wiersza // nieparzysta suma to ciemne pola
                // Odbieranie od 1. gracza kliknietego pola
                line = inF.readLine();
                destX = Character.getNumericValue(line.charAt(0));
                destY = Character.getNumericValue(line.charAt(1));
                // Wypisywanie na serwerze
                System.out.println("Tile: " + line);
                //TODO wysłanie planszy z wykonanym ruchem
                gameController.movePawn(gameController.getPawn(pawnX,pawnY),destX,destY);
                // sprawdzenie wykonanego ruchu
                // wysłanie informacji do 1. gracza w zależności od ruchu
                // outF.println("MOVE");
                // outF.println("CAPTURE");
                // outF.println("CANCEL");
                // wysłanie informacji do 2. gracza w zależności od ruchu
                // outS.println("MOVE");
                // outS.println("CAPTURE");

                //turn=SECOND;
            }
                if (gameController.whichPlayerTurn().equals("black")) {
                    // Odbieranie od socketa
                    line = inS.readLine();
                    // Wypisywanie na serwerze
                    System.out.println(line);
                    // Wysylanie do socketa
                    String temp = line.substring(0,2) + " --> " + line.substring(2);
                    outF.println(temp);
                   // turn=FIRST;
                }

                
            } while (true);

        } catch (IOException ex) {
            System.err.println("ex");
        }
    }

    private void sendMove(DataOutputStream out, String text) throws IOException {
        out.writeChars(text);

    }
}

