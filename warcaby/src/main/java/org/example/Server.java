package org.example;

import java.io.*;
import java.net.*;

public class Server {
    static BufferedReader in;
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
                System.out.println("First client connected");
                System.out.println("Waiting for the second player");

                Socket secondClient = serverSocket.accept();
                InputStream inputS = secondClient.getInputStream();
                BufferedReader inS = new BufferedReader(new InputStreamReader(inputS));
                line = inS.readLine();
                System.out.println(line);
                System.out.println("Second client connected");
                //TODO factory gier zależnie od typu
                GameFactory gameFactory = new GameControllerFactory();

                //GameController b = gameFactory.createGame("POLISH");
                Game g = new Game(firstClient, secondClient, gameFactory.createGame("POLISH"));
                Thread gTh = new Thread(g);
                gTh.start();



            }

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
