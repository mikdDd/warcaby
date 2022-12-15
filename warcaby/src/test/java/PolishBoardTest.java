import org.example.Client;
import org.example.Pawn;
import org.example.PolishCheckersBoard;
import static org.junit.Assert.*;
import org.junit.Test;

public class PolishBoardTest {

    //b.setFields();

    @Test
    public void drawBoardTest() {
        int fieldCount = 0;
        PolishCheckersBoard b = new PolishCheckersBoard();
        for(int i = 0; i < 10; i++)
        {
            for(int k = 0; k < 10; k++)
            {
                try {
                    System.out.print("["+b.fields[k][i].color+"]");
                    fieldCount++;
                }
                catch (NullPointerException e){
                    System.out.print("[     ]");
                }
            }
            System.out.println("");
        }
        assertEquals("Błąd planszy", b.pawnCount, fieldCount);
    }

    @Test
    public void moveTest() {

        PolishCheckersBoard b = new PolishCheckersBoard();
        //b.setFields();
        b.movePawn(b.fields[1][3],2,4);
        for(int i = 0; i < 10; i++)
        {
            for(int k = 0; k < 10; k++)
            {
                try {
                    System.out.print("["+b.fields[k][i].color+"("+k+i+")"+"]");
                }
                catch (NullPointerException e){
                    System.out.print("[   "+k+i +"    ]");
                }
            }
            System.out.println("");
        }
        if(b.fields[2][4] == null) System.out.println("TAK");

        
    }


}
