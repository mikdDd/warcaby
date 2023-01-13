import org.example.*;
import org.junit.Test;

import java.net.Socket;


import static org.junit.Assert.*;


/**Klasa testujaca serwer.*/
public class ServerTest {
  /**Metoda sprawdzajaca czy serwer nasluchuje na danym porcie
   * @param host nazwa hosta
   * @param port port serwera
   * @return true - serwer nasluchuje, false - serwer nie nasluchuje
   */
  public static boolean serverListening(final String host, final int port) {
      try (Socket ignored = new Socket(host, port)) {
          return true;
      } catch (Exception e) {
          return false;
      }
  }

  /**Test dzialania serwera*/
  @Test
  public void serverRunTest() {
    new Thread(Server::runServer).start();

    assertTrue("Błąd połączenia z sewerem", serverListening("localhost", 4444));
    assertNotNull("Blad instancji serwera",Server.getInstance());
  }
}
