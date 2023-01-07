import org.example.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class GameFactoryTest {

    @Test
    public void gameFactoryTest()
    {
        GameFactoryInterface gameFactoryInterface = new GameFactory();
        Game g1 = gameFactoryInterface.createGame("POLISH");
        Game g2 = gameFactoryInterface.createGame("ENGLISH");
        Game g3 = gameFactoryInterface.createGame("THAI");
        assertEquals("Błąd factory", PolishCheckers.class,g1.getClass());
        assertEquals("Błąd factory", EnglishCheckers.class,g2.getClass());
        assertEquals("Błąd factory", ThaiCheckers.class,g3.getClass());
    }

    @Test
    public void invalidArgGameFactoryTest()
    {
        GameFactoryInterface gameFactoryInterface = new GameFactory();
        Game g1 = gameFactoryInterface.createGame("ABCD");
        assertNull("Błąd factory", g1);
    }
}
