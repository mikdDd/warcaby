import org.example.DefaultBoard;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**Klasa implementujaca testy defaultowej planszy.*/
public class DefaultBoardTest {

  /**Test konstrukcji pol na planszy.*/
  @Test
  public void fieldConstructionTest() {
    int fieldCount = 0;
    final DefaultBoard defaultBoard = new DefaultBoard(10,10,10);

    for (int i = 0; i < defaultBoard.getFields()[0].length; i++) {
      for (int k = 0; k < defaultBoard.getFields().length; k++) {
        if (defaultBoard.getFields()[k][i] != null) {
          fieldCount++;
        }
      }
    }
    assertEquals("Błąd planszy", defaultBoard.getPawnCount() *2, fieldCount);
  }

  /**Test tworzenia planszy z za duza iloscia pionow.*/
  @Test(expected = IllegalArgumentException.class)
  public void boardExceptionTest() {
    new DefaultBoard(8, 8, 17);
  }
}
