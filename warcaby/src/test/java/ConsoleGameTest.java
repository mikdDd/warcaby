import org.example.Move;
import org.example.PolishCheckersBoard;
import static org.junit.Assert.*;
import org.junit.Test;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ConsoleGameTest {

    public static void main(String args[]) {

            Scanner myObj = new Scanner(System.in);  // Create a Scanner object

            //String move = myObj.nextLine();  // Read user input
            PolishCheckersBoard b = new PolishCheckersBoard();
        b.fields[3][3].setKing();
            //b.setFields();
            while (true) {



                for (int i = 0; i < 10; i++) {
                    for (int k = 0; k < 10; k++) {

                        try {

                            System.out.print("[" + b.fields[k][i].color + "(" + k + i + ")" );
                            if(b.fields[k][i].isKing){
                                System.out.print("D]");
                            } else {
                                System.out.print(" ]");
                            }
                        } catch (NullPointerException e) {
                            System.out.print("[    " + k + i + "    ]");
                        }
                    }
                    System.out.println("");
                }
               // if (b.fields[2][4] == null) System.out.println("TAK");
                String move = myObj.nextLine();
                int x1 = Character.getNumericValue(move.charAt(0));
                int y1 = Character.getNumericValue(move.charAt(1));
                int x2 = Character.getNumericValue(move.charAt(2));
                int y2 = Character.getNumericValue(move.charAt(3));
                System.out.println(x1+","+y1+","+x2+","+y2);
               // List<Move> kingMoves = b.checkKingPossibleMoves(b.fields[x1][y1]));
                List<Move> moves= new ArrayList<>();
                //= b.checkPossibleMoves(b.fields[x1][y1]));
                try {
                if(b.fields[x1][y1].isKing){
                    moves = b.checkKingPossibleMoves(b.fields[x1][y1]);
                } else {
                    moves = b.checkPossibleMoves(b.fields[x1][y1]);
                }
                } catch (NullPointerException e){}
                for(Move p : moves)
                {
                    try {
                        System.out.println(p.getX() + " " + p.getY());
                    } catch (NullPointerException e){}
                }
                b.movePawn(b.fields[x1][y1], x2, y2);
            }
        }

}
