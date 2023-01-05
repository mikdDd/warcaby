import static org.junit.Assert.*;

import org.example.Pawn;
import org.junit.Test;

public class PawnTest {

    @Test(expected = IllegalArgumentException.class)
    public void changePositionTest()
    {
        Pawn p = new Pawn(1,1,"black");
        p.changePosition(-5,-5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void illegalConstructorTest()
    {
        Pawn p = new Pawn(1,1,"brown");
    }

}
