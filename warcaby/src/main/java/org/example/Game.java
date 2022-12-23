package org.example;

import java.io.*;
import java.net.Socket;

public class Game implements Runnable{

    private Socket firstPlayer;
    private Socket secondPlayer;


    private final static int FIRST=1;
    private final static int SECOND=2;
    private static int turn=FIRST;


    public Game(Socket firstPlayer, Socket secondPlayer){
        this.firstPlayer = firstPlayer;
        this.secondPlayer= secondPlayer;


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
            outF.println("11");
            outS.println("11");
            //ilosc pionkow
            outF.println("10");
            outS.println("10");            
            //ustawienie pionkow
            outF.println("1819385878980929496989:10305070900121416181");
            outS.println("1819385878980929496989:10305070900121416181");
            

            String line;
          do 
          {
            if (turn==FIRST) 
            {
                // Odbieranie od 1. gracza kliknietego pionka
                line = inF.readLine();
                // Wypisywanie na serwerze
                System.out.println("Pawn: " + line);
                // Wysylanie do 1. gracza informacji o dostepnych polach
                outF.println("1416343656"); // musi być posortowana według kolumny a potem wiersza // nieparzysta suma to ciemne pola
                // Odbieranie od 1. gracza kliknietego pola
                line = inF.readLine();
                // Wypisywanie na serwerze
                System.out.println("Tile: " + line);
                // sprawdzenie wykonanego ruchu
                // wysłanie informacji do 1. gracza w zależności od ruchu
                // outF.println("MOVE");
                // outF.println("CAPTURE");
                // outF.println("CANCEL");
                // wysłanie informacji do 2. gracza w zależności od ruchu
                // outS.println("MOVE");
                // outS.println("CAPTURE");

                turn=SECOND;
            }
                if (turn==SECOND) {
                    // Odbieranie od socketa
                    line = inS.readLine();
                    // Wypisywanie na serwerze
                    System.out.println(line);
                    // Wysylanie do socketa
                    String temp = line.substring(0,2) + " --> " + line.substring(2);
                    outF.println(temp);
                    turn=FIRST;
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

