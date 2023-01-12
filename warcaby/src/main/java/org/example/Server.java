package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**Klasa serwera, wykorzystujaca wzorzec singleton.
 *
 */
public class Server {
  /**Instancja klasy Server.*/
  private static final Server instance = new Server();
  /**
   * Lista socketow oczekujacych na gre w warcay polskie.
   */
  private static final List<Socket> polishCheckersQueue = new ArrayList<>();
  /**Lista socketow oczekujacych na gre w warcay angielskie.*/
  private static final List<Socket> englishCheckersQueue = new ArrayList<>();
  /**Lista socketow oczekujacych na gre w warcay tajskie.*/
  private static final List<Socket> thaiCheckersQueue = new ArrayList<>();
  /**Interfejs GameFactory wykorzystywany do tworzenia obiektow klasy Game.*/
  private static final GameFactoryInterface gameFactoryInterface = new GameFactory();

  /**Metoda zwracajaca instancje klasy.
   * @return instancja klasy Server
   */
  public static Server getInstance() {
    return instance;
  }

  private Server() {}
  public static void runServer() {
    main(null);
  }

  public static void main(final String[] args) {
    try (ServerSocket serverSocket = new ServerSocket(4444)) {
      System.out.println("Server is listening on port 4444");

      while (true) {
        final Socket firstClient = serverSocket.accept();
        final InputStream inputF = firstClient.getInputStream();
        final BufferedReader inF = new BufferedReader(new InputStreamReader(inputF));
        final String line = inF.readLine();

        System.out.println(line);

        if ("GAME1".equals(line)) {
          polishCheckersQueue.add(firstClient);
        } else if ("GAME2".equals(line)) {
          englishCheckersQueue.add(firstClient);
        } else if ("GAME3".equals(line)) {
          thaiCheckersQueue.add(firstClient);
        }
        System.out.println("Client connected");


        if (polishCheckersQueue.size() >= 2) {
          final GameController g = new GameController(polishCheckersQueue.get(0), polishCheckersQueue.get(1), gameFactoryInterface.createGame("POLISH"));
          final Thread gTh = new Thread(g);
          gTh.start();
          polishCheckersQueue.remove(0);
          polishCheckersQueue.remove(0);
        }

        if (englishCheckersQueue.size() >= 2) {
          final GameController g = new GameController(englishCheckersQueue.get(0), englishCheckersQueue.get(1), gameFactoryInterface.createGame("ENGLISH"));
          final Thread gTh = new Thread(g);
          gTh.start();
          englishCheckersQueue.remove(0);
          englishCheckersQueue.remove(0);
        }

        if (thaiCheckersQueue.size() >= 2) {
          final GameController g = new GameController(thaiCheckersQueue.get(0), thaiCheckersQueue.get(1), gameFactoryInterface.createGame("THAI"));
          final Thread gTh = new Thread(g);
          gTh.start();
          thaiCheckersQueue.remove(0);
          thaiCheckersQueue.remove(0);
        }
      }
    } catch (IOException ex) {
      System.out.println("Server exception: " + ex.getMessage());
      ex.printStackTrace();
    }
  }
}
