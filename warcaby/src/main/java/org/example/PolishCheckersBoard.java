package org.example;

import javafx.geometry.Point2D;


import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

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
    public List<Point> checkPossibleMoves(Pawn pawn) {
        String color = pawn.color;
        int xPawn = pawn.xPosition;
        int yPawn = pawn.yPosition;
        boolean captureExist = false;
        List<Point> possibleMoves= new ArrayList<>();

        for (int x = xPawn - 1; x <= xPawn + 1; x+=2) {
            for (int y = yPawn - 1; y <= yPawn + 1; y+=2) {
                if (x > 0 && x < 10 && y > 0 && y < 10) {
                if (color.equals("white")) {                //poruszanie się białych pionków
                    if (fields[x][y] != null) {
                        if (fields[x][y].color == "black") {      //bicie
                                                                                                //TODO bicia w tył + skoro są we wszystkich kierunkach to można ujednolicić
                                if (x > xPawn) {
                                    if (fields[x + 1][y + 1] == null) {
                                        possibleMoves.add(new Point(x + 1, y + 1));
                                        captureExist = true;
                                    }

                                } else {
                                    if (fields[x - 1][y + 1] == null) {
                                        possibleMoves.add(new Point(x - 1, y + 1));
                                        captureExist = true;
                                    }
                                }

                        }
                    } else {
                        if (y > yPawn && x > 0 && x < 10) {
                            possibleMoves.add(new Point(x, y));
                        }
                    }
                } else {
                    if (fields[x][y] != null) {
                        if (fields[x][y].color == "white") {         //bicie

                                if (x > xPawn) {
                                    if (fields[x + 1][y - 1] == null) {         //czy za zbijanym pionkiem jest puste pole
                                        possibleMoves.add(new Point(x + 1, y - 1));
                                        captureExist = true;
                                    }

                                } else {
                                    if (fields[x - 1][y - 1] == null) {
                                        possibleMoves.add(new Point(x - 1, y - 1));
                                        captureExist = true;
                                    }
                                }

                        }
                    } else {
                        if (y < yPawn && x > 0 && x < 10) {
                            possibleMoves.add(new Point(x, y));
                        }
                    }
                }

            }
            }
        }
        //jezeli wystapilo bicie usuwamy z listy wszystkie ruchy nie będące biciami
        if(captureExist) {

            //bicia sa ruchami na pole w odleglosci 2 od pionka
            possibleMoves.removeIf(point -> Math.abs(point.x - xPawn) < 2);
        }
        return possibleMoves;
    }
    public List<Point> checkKingPossibleMoves(Pawn pawn) {
        String color = pawn.color;
        int xPawn = pawn.xPosition;
        int yPawn = pawn.yPosition;
        boolean captureExist = false;
        List<Point> possibleMoves= new ArrayList<>();

        for(int x = 0; x<10; x++)
        {
            for(int y = 0; y < 10; y++)
            {
                if(Math.abs(x-xPawn)==Math.abs(y-yPawn)) {          //pola na przekątnej
                    if (fields[x][y] != null) {
                        if (fields[x][y].color != pawn.color) {         //bicie
                                                                                    //TODO dokończyć bicie dla damki
                                if (x > xPawn) {
                                    if (fields[x + 1][y - 1] == null) {         //czy za zbijanym pionkiem jest puste pole
                                        possibleMoves.add(new Point(x + 1, y - 1));
                                        captureExist = true;
                                    }

                                } else {
                                    if (fields[x - 1][y - 1] == null) {
                                        possibleMoves.add(new Point(x - 1, y - 1));
                                        captureExist = true;
                                    }
                                }

                        }
                    } else {
                        if (y < yPawn && x > 0 && x < 10) {
                            possibleMoves.add(new Point(x, y));
                        }
                    }
                }
            }
        }
        return null;
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
        Point p = new Point(x,y);
        if(checkPossibleMoves(pawn).contains(p)) {

            //sprawdz czy bylo bicie
            if(x==xPawn+2){
                if(y>yPawn){
                    fields[xPawn+1][yPawn+1].capture();
                } else {
                    fields[xPawn+1][yPawn-1].capture();
                }
            } else if (x==pawn.xPosition-2) {
                if(y>yPawn){
                    fields[xPawn-1][yPawn+1].capture();
                } else {
                    fields[xPawn-1][yPawn-1].capture();
                }
            }
            pawn.changePosition(x,y);
        }

        this.setFields();
    }

    @Override
    public boolean checkIfWon() {
        return false;
    }
}
