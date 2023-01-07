import org.example.EnglishCheckers;
import org.example.Game;
import org.example.PolishCheckers;
import org.example.ThaiCheckers;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class KingsTest {

    @Test
    public void polishKingMoveTest(){  //moze poruszac sie o dowolna liczbe pol we wszystkich kierunkach
        Game game = new PolishCheckers();
        game.getPawn(6,3).setKing();
        game.movePawn(game.getPawn(6,3),7,4);
        String s = game.possibleMovesToString(game.getPawn(7,4));
        assertEquals("Błąd planszy", "636585", s);
    }
    @Test
    public void englishKingMoveTest(){  //porusza sie o jedno pole w danym kierunku
        Game game = new EnglishCheckers();
        game.getPawn(5,2).setKing();
        //game.movePawn(game.getPawn(5,2),6,3);
        String s = game.possibleMovesToString(game.getPawn(5,2));
        assertEquals("Błąd planszy", "4363", s);
    }
    @Test
    public void thaiKingMoveTest(){  //moze poruszac sie o dowolna liczbe pol we wszystkich kierunkach
        Game game = new ThaiCheckers();
        game.getPawn(4,1).setKing();
        game.movePawn(game.getPawn(4,1),5,2);
        String s = game.possibleMovesToString(game.getPawn(5,2));
        assertEquals("Błąd planszy", "414334256374", s);
    }
}
