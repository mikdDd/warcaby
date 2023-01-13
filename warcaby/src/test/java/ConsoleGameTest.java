import org.example.*;

import java.util.Scanner;

/**Klasa implementujaca konsolowa wersje gry w warcaby.
 * Przeznaczona do testow nie wykorzystujacych GUI
 *
 */
public class ConsoleGameTest {
  public static void main(final String[] args) {
    final Scanner myObj = new Scanner(System.in);

    final GameFactoryInterface gameFactory = new GameFactory();
    final Game b = gameFactory.createGame("POLISH");

    while (true) {
      System.out.println(b.whichPlayerTurn());
      System.out.println(b.boardToString());


      for (int i = 0; i < b.getBoardSize(); i++) {
         for (int k = 0; k < b.getBoardSize(); k++) {
           try {
               System.out.print("[" + b.getPawn(k, i).getColor() + "(" + k + i + ")" );
               if (b.getPawn(k, i).isKing()) {
                 System.out.print("D]");
               } else {
                 System.out.print(" ]");
               }
           } catch (NullPointerException e) {
             System.out.print("[    " + k + i + "    ]");
           }
         }
         System.out.println();
      }

      final String move = myObj.nextLine();
      final int x1 = Character.getNumericValue(move.charAt(0));
      final int y1 = Character.getNumericValue(move.charAt(1));
      final int x2 = Character.getNumericValue(move.charAt(2));
      final int y2 = Character.getNumericValue(move.charAt(3));

      System.out.println(b.possibleMovesToString(b.getPawn(x1,y1)));

      b.movePawn(b.getPawn(x1,y1), x2, y2);

    }
  }
}
