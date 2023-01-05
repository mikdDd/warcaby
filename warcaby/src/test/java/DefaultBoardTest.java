
import org.example.DefaultBoard;
import org.example.Pawn;
import org.example.PolishCheckers;

import static org.junit.Assert.*;
import org.junit.Test;

public class DefaultBoardTest {

    //b.setFields();

    @Test
    public void fieldConstructionTest() {
        int fieldCount = 0;
        DefaultBoard defaultBoard = new DefaultBoard(10,10,10);
        for(int i = 0; i < defaultBoard.fields[0].length;i++)
        {
            for(int k = 0; k < defaultBoard.fields.length;k++)
            {
                if(defaultBoard.fields[k][i] != null)
                {
                    fieldCount++;
                }
            }
        }
        assertEquals("Błąd planszy", defaultBoard.pawnCount*2, fieldCount);
    }

    @Test(expected = IllegalArgumentException.class)
    public void boardExceptionTest() {

      DefaultBoard defaultBoard = new DefaultBoard(8,8,17);
        
    }



}
