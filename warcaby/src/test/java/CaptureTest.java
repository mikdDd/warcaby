import org.example.EnglishCheckers;
import org.example.Game;
import org.example.PolishCheckers;
import org.example.ThaiCheckers;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**Klasa testujaca bicie pionkow.
 *
 */
public class CaptureTest {

  /**Test bicia pionkiem w warcabach polskich.*/
  @Test
  public void polishPawnCaptureTest() {
    final Game game = new PolishCheckers();
    game.movePawn(game.getPawn(6,3),7,4);
    game.movePawn(game.getPawn(9,6),8,5);
    game.movePawn(game.getPawn(7,4),9,6);
    final String color = game.getPawn(9, 6).getColor();
    assertEquals("Błąd ruchu pionków", "white", color);
    assertNull("Błąd bicia", game.getPawn(8, 5));
  }


  /**Test bicia pionkiem w warcabach angielskich.*/
  @Test
  public void englishPawnCaptureTest() {
    final Game game = new EnglishCheckers();
    game.movePawn(game.getPawn(5,2),4,3);
    game.movePawn(game.getPawn(6,5),5,4);
    game.movePawn(game.getPawn(4,3),6,5);
    final String color = game.getPawn(6, 5).getColor();
    assertEquals("Błąd ruchu pionków", "white", color);
    assertNull("Błąd bicia", game.getPawn(5, 4));
  }

    /**Test bicia pionkiem w warcabach tajskich.*/
  @Test
  public void thaiPawnCaptureTest() {
    final Game game = new ThaiCheckers();
    game.movePawn(game.getPawn(4,1),5,2);
    game.movePawn(game.getPawn(3,6),2,5);
    game.movePawn(game.getPawn(5,2),4,3);
    game.movePawn(game.getPawn(2,5),3,4);
    game.movePawn(game.getPawn(4,3),2,5);
    final String color = game.getPawn(2, 5).getColor();
    assertEquals("Błąd ruchu pionków", "white", color);
    assertNull("Błąd bicia", game.getPawn(3, 4));
  }
}
