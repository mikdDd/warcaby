import org.example.Pawn;
import org.junit.Test;

/**Klasa testujaca klase pionka.*/
public class PawnTest {
  /**Test zmiany pozycji pionka.*/
  @Test(expected = IllegalArgumentException.class)
  public void changePositionTest() {
    Pawn p = new Pawn(1,1,"black");
    p.changePosition(-5,-5);
  }

  /**Test blednej konstrukcji pionka.*/
  @Test(expected = IllegalArgumentException.class)
  public void illegalConstructorTest() {
    new Pawn(1, 1, "brown");
  }
}
