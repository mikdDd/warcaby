import org.example.PolishCheckersBoard;
import static org.junit.Assert.*;
import org.junit.Test;

import java.awt.*;
import java.io.IOException;
import java.util.Scanner;


public class ConsoleGameTest {

    public static void main(String args[]) {

            Scanner myObj = new Scanner(System.in);  // Create a Scanner object

            //String move = myObj.nextLine();  // Read user input
            PolishCheckersBoard b = new PolishCheckersBoard();
            //b.setFields();
            while (true) {



                for (int i = 0; i < 10; i++) {
                    for (int k = 0; k < 10; k++) {
                        try {
                            System.out.print("[" + b.fields[k][i].color + "(" + k + i + ")" + "]");
                        } catch (NullPointerException e) {
                            System.out.print("[   " + k + i + "    ]");
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
                for(Point p : b.checkPossibleMoves(b.fields[x1][y1]))
                {
                    try {
                        System.out.println(p.x + " " + p.y);
                    } catch (NullPointerException e){}
                }
                b.movePawn(b.fields[x1][y1], x2, y2);
            }
        }

}
