import org.example.EnglishCheckers;
import org.example.Game;
import org.example.PolishCheckers;
import org.example.ThaiCheckers;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CaptureTest {
    @Test
    public void polishPawnCaptureTest()
    {
        Game game = new PolishCheckers();
        game.movePawn(game.getPawn(6,3),7,4);
        game.movePawn(game.getPawn(9,6),8,5);
        game.movePawn(game.getPawn(7,4),9,6);
        String color = game.getPawn(9,6).color;
        assertEquals("Błąd ruchu pionków", "white", color);
        assertNull("Błąd bicia", game.getPawn(8, 5));
    }
    @Test
    public void englishPawnCaptureTest()
    {
        Game game = new EnglishCheckers();
        game.movePawn(game.getPawn(5,2),4,3);
        game.movePawn(game.getPawn(6,5),5,4);
        game.movePawn(game.getPawn(4,3),6,5);
        String color = game.getPawn(6,5).color;
        assertEquals("Błąd ruchu pionków", "white", color);
        assertNull("Błąd bicia", game.getPawn(5, 4));
    }
    @Test
    public void thaiPawnCaptureTest()
    {
        Game game = new ThaiCheckers();
        game.movePawn(game.getPawn(4,1),5,2);
        game.movePawn(game.getPawn(3,6),2,5);
        game.movePawn(game.getPawn(5,2),4,3);
        game.movePawn(game.getPawn(2,5),3,4);
        game.movePawn(game.getPawn(4,3),2,5);
        String color = game.getPawn(2,5).color;
        assertEquals("Błąd ruchu pionków", "white", color);
        assertNull("Błąd bicia", game.getPawn(3, 4));
    }
}
