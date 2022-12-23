
import org.example.Pawn;
import org.example.PolishCheckers;

import static org.junit.Assert.*;
import org.junit.Test;

public class PolishBoardTest {

    //b.setFields();

    @Test
    public void drawBoardTest() {
        int fieldCount = 0;
        PolishCheckers b = new PolishCheckers();
        for(int i = 0; i < 10; i++)
        {
            for(int k = 0; k < 10; k++)
            {
                try {
                    System.out.print("["+b.board.fields[k][i].color+"]");
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

        PolishCheckers b = new PolishCheckers();
        //b.setFields();
        b.movePawn(b.board.fields[1][3],2,4);
        b.movePawn(b.board.fields[4][6],3,5);
        b.movePawn(b.board.fields[2][4],4, 6);
        for(int i = 0; i < 10; i++)
        {
            for(int k = 0; k < 10; k++)
            {
                try {
                    System.out.print("["+b.board.fields[k][i].color+"("+k+i+")"+"]");
                }
                catch (NullPointerException e){
                    System.out.print("[   "+k+i +"    ]");
                }
            }
            System.out.println("");
        }
        if(b.board.fields[2][4] == null) System.out.println("TAK");

        
    }



}
