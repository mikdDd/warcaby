package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PolishCheckersBoard extends Board{
    public int xSize = 10; //poziomo
    public int ySize = 10;  //pionowo
    public int pawnCount = 40;
    public Pawn[][] fields = new Pawn[xSize][ySize];
    public List<Pawn> pawnList = new ArrayList<>();

    public PolishCheckersBoard() {
        setPawnList();
        setFields();

    }

    @Override
    public void setFields() {

        fields = new Pawn[xSize][ySize];
        for(Pawn pawn : pawnList)
       {
           int pawnX = pawn.xPosition;
           int pawnY = pawn.yPosition;
           fields[pawnX][pawnY] = pawn;

       }
    }

    @Override
    void setPawnList() {
        for(int i = 0; i < ySize; i++)
        {
            for(int k = 0; k < xSize; k++)
            {
                if(i!=4 && i != 5 && ((k%2==0&&i%2==0)||(k%2==1&&i%2==1))) {
                    String color="white";
                    if(i>5)color="black";
                    Pawn pawn = new Pawn(k,i,color);
                    //fields[k][i] = pawn;
                    pawnList.add(pawn);
                }
            }
        }
    }

    @Override
    public void movePawn(Pawn pawn, int x, int y) {
        int xPawn = pawn.xPosition;
        int yPawn = pawn.yPosition;
        String color = pawn.color;

        if(color.equals("white")){
          if( (x == xPawn + 1 && y == yPawn + 1) || (x == xPawn - 1 && y == yPawn + 1) ) {
              if(fields[x][y] != null) {
                  if(fields[x][y].color.equals("black")) {
                   //bicie
                  }
              } else {
                  pawn.changePosition(x,y);
              }
          }

        } else {
            ;
        }
        this.setFields();
    }

    @Override
    public boolean checkIfWon() {
        return false;
    }
}
