package org.example;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Server {
    static BufferedReader in;
    static List<Socket> polishCheckersQueue = new ArrayList<>();
    static List<Socket> englishCheckersQueue = new ArrayList<>();
    static List<Socket> thaiCheckersQueue = new ArrayList<>();
    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(4444)) {

            System.out.println("Server is listening on port 4444");
            //TODO kolejkowanie graczy (do zrobienia jak będzie już dopracowana komunikacja)
            while (true) 
            {
                Socket firstClient = serverSocket.accept();
                //TODO wyslać tu info o typie gry wybranym przez klienta
                InputStream inputF = firstClient.getInputStream();
                BufferedReader inF = new BufferedReader(new InputStreamReader(inputF));
                String line = inF.readLine();
                System.out.println(line);
                if(line.equals("GAME1")){
                    polishCheckersQueue.add(firstClient);
                } else if (line.equals("GAME2")) {
                   englishCheckersQueue.add(firstClient);
                } else if (line.equals("GAME3")) {
                    thaiCheckersQueue.add(firstClient);
                }
                System.out.println("Client connected");
                /*
                System.out.println("Waiting for the second player");

                Socket secondClient = serverSocket.accept();
                InputStream inputS = secondClient.getInputStream();
                BufferedReader inS = new BufferedReader(new InputStreamReader(inputS));
                line = inS.readLine();
                System.out.println(line);
                if(line.equals("GAME1")){
                    polishCheckersQueue.add(secondClient);
                } else if (line.equals("GAME2")) {
                    englishCheckersQueue.add(secondClient);
                } else if (line.equals("GAME3")) {
                   thaiCheckersQueue.add(secondClient);
                }
                System.out.println("Second client connected");
                //TODO factory gier zależnie od typu
                */
                GameFactory gameFactory = new GameControllerFactory();
                if(polishCheckersQueue.size()>=2){

                    Game g = new Game(polishCheckersQueue.get(0), polishCheckersQueue.get(1), gameFactory.createGame("GAME1"));
                    Thread gTh = new Thread(g);
                    gTh.start();
                    polishCheckersQueue.remove(0);
                    polishCheckersQueue.remove(0);
                }
                if(englishCheckersQueue.size()>=2){

                    Game g = new Game(englishCheckersQueue.get(0), englishCheckersQueue.get(1), gameFactory.createGame("GAME2"));
                    Thread gTh = new Thread(g);
                    gTh.start();
                    englishCheckersQueue.remove(0);
                    englishCheckersQueue.remove(0);
                }
                if(thaiCheckersQueue.size()>=2){

                    Game g = new Game(thaiCheckersQueue.get(0), thaiCheckersQueue.get(1), gameFactory.createGame("GAME3"));
                    Thread gTh = new Thread(g);
                    gTh.start();
                    thaiCheckersQueue.remove(0);
                    thaiCheckersQueue.remove(0);
                }

                //GameController b = gameFactory.createGame("POLISH");



            }

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
