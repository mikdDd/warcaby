import org.example.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**Klasa testujaca typy gier.*/
public class GameTypesTest {

  /**Test ruchu w warcabach polskich*/
  @Test
  public void polishCheckersMoveTest() {
    final Game game = new PolishCheckers();
    game.movePawn(game.getPawn(6,3),7,4);
    final String s = game.possibleMovesToString(game.getPawn(7,4));
    assertEquals("Błąd planszy", "6585", s);
  }

  /**Test blednego ruchu w warcabach polskich.*/
  @Test
  public void polishCheckersBadMoveTest() {
    final Game game = new PolishCheckers();
    game.movePawn(game.getPawn(0,3),-1,-1);
    final String s = game.possibleMovesToString(game.getPawn(0,3));
    assertEquals("Błąd planszy", "14", s);
  }

    /**Test ruchu w warcabach angielskich*/
    @Test
    public void englishCheckersMoveTest()
    {
        final Game game = new EnglishCheckers();
        game.movePawn(game.getPawn(5,2),4,3);
        final String s = game.possibleMovesToString(game.getPawn(4,3));
        assertEquals("Błąd planszy", "3454", s);
    }

    /**Test blednego ruchu w warcabach angielskich.*/
    @Test
    public void englishCheckersBadMoveTest() {
      final Game game = new EnglishCheckers();
      game.movePawn(game.getPawn(5,2),50,50);
      final String s = game.possibleMovesToString(game.getPawn(5,2));
      assertEquals("Błąd planszy", "4363", s);
    }

    /**Test ruchu w warcabach tajskich*/
    @Test
    public void thaiCheckersMoveTest() {
      final Game game = new ThaiCheckers();
      game.movePawn(game.getPawn(4,1),3,2);
      final String s = game.possibleMovesToString(game.getPawn(3,2));
      assertEquals("Błąd planszy", "2343", s);
    }

    /**Test blednego ruchu w warcabach tajskich.*/
    @Test
    public void thaiCheckersBadMoveTest() {
      final Game game = new ThaiCheckers();
      game.movePawn(game.getPawn(2,1),-1,-1);
      final String s = game.possibleMovesToString(game.getPawn(2,1));
      assertEquals("Błąd planszy", "1232", s);
    }

    /**Test metody coordsRelation dla takich samych wspolrzednych.*/
    @Test
    public void equalCoordsRelationTest() {
        final int xFlag = GameTypeTemplate.coordsRelation(5,5,5,5)[0];
        final int yFlag = GameTypeTemplate.coordsRelation(5,5,5,5)[0];
        assertEquals("Błąd wspolrzednych", 0, xFlag);
        assertEquals("Błąd wspolrzednych", 0, yFlag);
    }

    /**Test wysylanie planszy jako String.*/
    @Test
    public void initialBoardToStringTest() {
      Game game;
      String str;
      final GameFactoryInterface gameFactory = new GameFactory();
      game = gameFactory.createGame("Polish");
      str = game.boardToString();
      assertEquals("Błąd wypisywania planszy warcabow polskich do stringa", "1030507090012141618112325272920323436383:8969492909987858381887674727079676563616", str);

      game = gameFactory.createGame("English");
      str = game.boardToString();
      assertEquals("Błąd wypisywania planszy warcabow angielskich do stringa", "103050700121416112325272:674727077656361665452505", str);

      game = gameFactory.createGame("Thai");
      str = game.boardToString();
      assertEquals("Błąd wypisywania planszy warcabow tajskich do stringa", "1030507001214161:6747270776563616", str);

    }

}
