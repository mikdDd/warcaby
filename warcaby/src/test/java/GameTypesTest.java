import org.example.EnglishCheckers;
import org.example.Game;
import org.example.PolishCheckers;
import org.example.ThaiCheckers;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GameTypesTest {
    @Test
    public void polishCheckersMoveTest()
    {
        Game game = new PolishCheckers();
        game.movePawn(game.getPawn(6,3),7,4);
        String s = game.possibleMovesToString(game.getPawn(7,4));
        assertEquals("Błąd planszy", "6585", s);
    }
    @Test
    public void polishCheckersBadMoveTest()       //pionek nie ruszy się na błędne pole
    {
        Game game = new PolishCheckers();
        game.movePawn(game.getPawn(0,3),-1,-1);
        String s = game.possibleMovesToString(game.getPawn(0,3));
        assertEquals("Błąd planszy", s, "14");
    }
    @Test
    public void englishCheckersMoveTest()
    {
        Game game = new EnglishCheckers();
        game.movePawn(game.getPawn(5,2),4,3);
        String s = game.possibleMovesToString(game.getPawn(4,3));
        assertEquals("Błąd planszy", "3454", s);
    }
    @Test
    public void englishCheckersBadMoveTest()       //pionek nie ruszy się na błędne pole
    {
        Game game = new EnglishCheckers();
        game.movePawn(game.getPawn(0,3),50,50);
        String s = game.possibleMovesToString(game.getPawn(0,3));
        assertEquals("Błąd planszy", "", s);
    }
    @Test
    public void thaiCheckersMoveTest()
    {
        Game game = new ThaiCheckers();
        game.movePawn(game.getPawn(4,1),3,2);
        String s = game.possibleMovesToString(game.getPawn(3,2));
        assertEquals("Błąd planszy", "2343", s);
    }

    @Test
    public void thaiCheckersBadMoveTest()       //pionek nie ruszy się na błędne pole
    {
        Game game = new ThaiCheckers();
        game.movePawn(game.getPawn(2,1),-1,-1);
        String s = game.possibleMovesToString(game.getPawn(0,3));
        assertEquals("Błąd planszy", "", s);
    }
}
