import org.example.EnglishCheckers;
import org.example.GameController;
import org.example.PolishCheckers;
import org.example.ThaiCheckers;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
//TODO testy damek
public class GameTypesTest {
    @Test
    public void polishCheckersMoveTest()
    {
        GameController gameController = new PolishCheckers();
        gameController.movePawn(gameController.getPawn(6,3),7,4);
        String s = gameController.possibleMovesToString(gameController.getPawn(7,4));
        assertEquals("Błąd planszy", "6585", s);
    }
    @Test
    public void polishCheckersBadMoveTest()       //pionek nie ruszy się na błędne pole
    {
        GameController gameController = new PolishCheckers();
        gameController.movePawn(gameController.getPawn(0,3),-1,-1);
        String s = gameController.possibleMovesToString(gameController.getPawn(0,3));
        assertEquals("Błąd planszy", s, "14");
    }
    @Test
    public void englishCheckersMoveTest()
    {
        GameController gameController = new EnglishCheckers();
        gameController.movePawn(gameController.getPawn(5,2),4,3);
        String s = gameController.possibleMovesToString(gameController.getPawn(4,3));
        assertEquals("Błąd planszy", "3454", s);
    }
    @Test
    public void englishCheckersBadMoveTest()       //pionek nie ruszy się na błędne pole
    {
        GameController gameController = new EnglishCheckers();
        gameController.movePawn(gameController.getPawn(0,3),50,50);
        String s = gameController.possibleMovesToString(gameController.getPawn(0,3));
        assertEquals("Błąd planszy", "", s);
    }
    @Test
    public void thaiCheckersMoveTest()
    {
        GameController gameController = new ThaiCheckers();
        gameController.movePawn(gameController.getPawn(4,1),3,2);
        String s = gameController.possibleMovesToString(gameController.getPawn(3,2));
        assertEquals("Błąd planszy", "2343", s);
    }

    @Test
    public void thaiCheckersBadMoveTest()       //pionek nie ruszy się na błędne pole
    {
        GameController gameController = new ThaiCheckers();
        gameController.movePawn(gameController.getPawn(2,1),-1,-1);
        String s = gameController.possibleMovesToString(gameController.getPawn(0,3));
        assertEquals("Błąd planszy", "", s);
    }
}
