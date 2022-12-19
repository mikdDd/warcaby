package org.example;

import javafx.geometry.Point2D;


import java.awt.*;
import java.util.*;
import java.util.List;
//TODO "globalne" sprawdzenie czy ktoryś z pionków gracza ma bicie
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
    public List<Move> checkPossibleMoves(Pawn pawn) {
        String color = pawn.color;
        int xPawn = pawn.xPosition;
        int yPawn = pawn.yPosition;
        boolean captureExist = false;
        List<Move> possibleMoves= new ArrayList<>();

        for (int x = xPawn - 1; x <= xPawn + 1; x+=2) {                     //TODO ujednolic i zmniejszyc liczbe ifów
            for (int y = yPawn - 1; y <= yPawn + 1; y+=2) {
                if (x >= 0 && x < 10 && y >= 0 && y < 10) {
                if (color.equals("white")) {                //poruszanie się białych pionków
                    if (fields[x][y] != null) {
                        if (Objects.equals(fields[x][y].color, "black")) {      //bicie
                                                                                                //TODO dla x i y można w zaleznosci czy >,< #Pawn mozna ustawiac flagi i zrobi to w jednym ifie
                                if (x > xPawn) {
                                    if(y>yPawn) {
                                      if ( x+1 < 10 && y + 1 < 10 && fields[x + 1][y + 1] == null ) {
                                        possibleMoves.add(new Move(x + 1, y + 1,true));
                                        captureExist = true;
                                      }
                                    } else {
                                        if ( x + 1 < 10 && y - 1 >= 0 && fields[x + 1][y - 1] == null ) {
                                            possibleMoves.add(new Move(x + 1, y - 1,true));
                                            captureExist = true;
                                        }
                                    }

                                } else {
                                    if(y > yPawn) {
                                        if (x - 1 >= 0 && y + 1 < 10 && fields[x - 1][y + 1] == null) {
                                            possibleMoves.add(new Move(x - 1, y + 1,true));
                                            captureExist = true;
                                        }
                                    } else {
                                        if ( x - 1 >= 0 && y - 1 >= 0 && fields[x - 1][y - 1] == null) {
                                            possibleMoves.add(new Move(x - 1, y - 1,true));
                                            captureExist = true;
                                        }
                                    }
                                }
                        }
                    } else {
                        if (y > yPawn) {
                            possibleMoves.add(new Move(x, y,false));
                        }
                    }
                } else {                            //poruszanie czarnych pionków
                    if (fields[x][y] != null) {
                        if (Objects.equals(fields[x][y].color, "white")) {         //bicie

                            if (x > xPawn) {
                                if(y>yPawn) {
                                    if ( x+1 < 10 && y + 1 < 10 && fields[x + 1][y + 1] == null ) {
                                        possibleMoves.add(new Move(x + 1, y + 1,true));
                                        captureExist = true;
                                    }
                                } else {
                                    if ( x + 1 < 10 && y - 1 >= 0 && fields[x + 1][y - 1] == null ) {
                                        possibleMoves.add(new Move(x + 1, y - 1,true));
                                        captureExist = true;
                                    }
                                }

                            } else {
                                if(y > yPawn) {
                                    if (x - 1 >= 0 && y + 1 < 10 && fields[x - 1][y + 1] == null) {
                                        possibleMoves.add(new Move(x - 1, y + 1,true));
                                        captureExist = true;
                                    }
                                } else {
                                    if ( x - 1 >= 0 && y - 1 >= 0 && fields[x - 1][y - 1] == null) {
                                        possibleMoves.add(new Move(x - 1, y - 1,true));
                                        captureExist = true;
                                    }
                                }
                            }

                        }
                    } else {
                        if (y < yPawn) {
                            possibleMoves.add(new Move(x, y,false));
                        }
                    }
                }

            }
            }
        }
        //jezeli wystapilo bicie usuwamy z listy wszystkie ruchy nie będące biciami
        if(captureExist) {

            possibleMoves.removeIf(move -> !move.isCapture());
        }
        return possibleMoves;
    }
    public List<Move> checkKingPossibleMoves(Pawn pawn) {
        String color = pawn.color;
        int xPawn = pawn.xPosition;
        int yPawn = pawn.yPosition;
        boolean captureExist = false;
        List<Move> possibleMoves = new ArrayList<>();

        for(int x = 0; x<10; x++)
        {
            for(int y = 0; y < 10; y++)
            {
                if(Math.abs(x-xPawn)==Math.abs(y-yPawn)) {          //pola na przekątnej
                    //System.out.println("X:"+x+"Y:"+y);
                    if (fields[x][y] != null) {
                        if (!Objects.equals(fields[x][y].color, pawn.color)) {         //bicie

                            if (x > xPawn) {
                                if(y>yPawn) {
                                    if ( x+1 < 10 && y + 1 < 10 && fields[x + 1][y + 1] == null ) {
                                        possibleMoves.add(new Move(x + 1, y + 1, true));
                                        captureExist = true;
                                    }
                                } else {
                                    if ( x + 1 < 10 && y - 1 >= 0 && fields[x + 1][y - 1] == null ) {
                                        possibleMoves.add(new Move(x + 1, y - 1, true));
                                        captureExist = true;
                                    }
                                }

                            } else {
                                if(y > yPawn) {
                                    if (x - 1 >= 0 && y + 1 < 10 && fields[x - 1][y + 1] == null) {
                                        possibleMoves.add(new Move(x - 1, y + 1, true));
                                        captureExist = true;
                                    }
                                } else {
                                    if ( x - 1 >= 0 && y - 1 >= 0 && fields[x - 1][y - 1] == null) {
                                        possibleMoves.add(new Move(x - 1, y - 1, true));
                                        captureExist = true;
                                    }
                                }
                            }

                        }
                    } else {
                        //System.out.println("ASDASD");
                            possibleMoves.add(new Move(x, y, false));

                    }
                }
            }
        }
        if(captureExist) {
            //System.out.println("CAPTURE");
            possibleMoves.removeIf(move -> !move.isCapture());
        }
        return possibleMoves;
    }


    @Override
    public void setFields() {

        fields = new Pawn[xSize][ySize];
        for(Pawn pawn : pawnList)
       {
           if(pawn.isActive) {
               int pawnX = pawn.xPosition;
               int pawnY = pawn.yPosition;
               fields[pawnX][pawnY] = pawn;
           }
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
        Move moveWithCapture = new Move(x,y,true);
        Move moveWithNoCapture = new Move(x,y,false);
        if(moveWithNoCapture.equals(moveWithCapture)) System.out.println("KAKAKA");
        List<Move> movesList;
        if(pawn.isKing){
            movesList = checkKingPossibleMoves(pawn);

        } else {
            movesList = checkPossibleMoves(pawn);

        }
        if(movesList.contains(moveWithCapture)) {
            System.out.println("zBICIem");
            if(x>xPawn){
                if(y>yPawn){
                    fields[x-1][y-1].capture();
                } else {
                    fields[x-1][y+1].capture();
                }
            } else if (x<xPawn) {
                if(y>yPawn){
                    fields[x+1][y-1].capture();
                } else {
                    fields[x+1][y+1].capture();
                }
            }
            pawn.changePosition(x,y);
        } else if(movesList.contains(moveWithNoCapture)) {
            System.out.println("BEZBICIA");
            pawn.changePosition(x,y);
        }

        this.setFields();
        checkKings();
    }
    public void checkKings()
    {
        for(int x = 0; x < 10; x++)
        {  try {
            if (Objects.equals(fields[x][0].color, "black")) {
                fields[x][0].setKing();
            }
            if (Objects.equals(fields[x][9].color, "white")) {
                fields[x][9].setKing();
            }
        } catch (NullPointerException e){}
        }
    }
    @Override
    public boolean checkIfWon() {
        return false;
    }
}
