package org.example;

import javafx.geometry.Point2D;


import java.awt.*;
import java.util.*;
import java.util.List;
//TODO sprawdzanie czy nie chcemy bić niaktywnego(zbitego) pionka
public class PolishCheckersBoard extends Board{
    public int xSize = 10; //poziomo
    public int ySize = 10;  //pionowo
    public int pawnCount = 40;
    public Pawn[][] fields = new Pawn[xSize][ySize];
    public List<Pawn> pawnList = new ArrayList<>();
    private String turn = "white";
    private Pawn multipleCapturePawn;

    public PolishCheckersBoard() {
        setPawnList();
        setFields();

    }
    public List<Move> checkPossibleMoves(Pawn pawn) {

        String color = pawn.color;
        int xPawn = pawn.xPosition;
        int yPawn = pawn.yPosition;
        boolean captureExist = false;
        List<Move> possibleMoves = new ArrayList<>();
        List<Pawn> pawnWithCapture = this.playerPawnWithCaptureList(pawn.color);
        if(this.multipleCapturePawn != null && !pawn.equals(multipleCapturePawn) && this.whichPlayerTurn().equals(pawn.color)){
            return possibleMoves;
        }
        if(!pawnWithCapture.isEmpty() && !pawnWithCapture.contains(pawn)){
            return possibleMoves;
        }
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
        if(this.multipleCapturePawn != null && !pawn.equals(multipleCapturePawn) && this.multipleCapturePawn.color.equals(pawn.color)){
            return possibleMoves;
        }
        if(!this.playerPawnWithCaptureList(pawn.color).isEmpty() && !this.playerPawnWithCaptureList(pawn.color).contains(pawn)){
            return possibleMoves;
        }
        for(int x = 0; x<10; x++)
        {
            for(int y = 0; y < 10; y++)
            {
                if(Math.abs(x-xPawn)==Math.abs(y-yPawn)) {          //pola na przekątnej
                    //System.out.println("X:"+x+"Y:"+y);
                    if (fields[x][y] != null && fields[x][y].isActive) {
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

    public boolean canPawnCapture(Pawn pawn) {
        int xPawn = pawn.xPosition;
        int yPawn = pawn.yPosition;
        int xFlag=0;
        int yFlag=0;


        for (int x = xPawn - 1; x <= xPawn +1; x+=2) {
            for (int y = yPawn -1 ; y <= yPawn +1; y+=2) {
                if (x >= 0 && x < 10 && y >= 0 && y < 10) {
                        if (fields[x][y] != null  && fields[x][y].isActive) {
                            if (!Objects.equals(fields[x][y].color, pawn.color)) {      //bicie


                                if(x > xPawn && x+1 < 10){xFlag = 1;}
                                else if(x < xPawn && x-1 >=0 ){
                                    xFlag = -1;
                                }
                                if(y > yPawn && y + 1 < 10 ){yFlag = 1;}
                                else if(y < yPawn && y-1 >=0 ){
                                    yFlag = -1;
                                }
                                if(xFlag != 0 && yFlag != 0 && fields[x+xFlag][y+yFlag] == null){
                                    return true;
                                }


                            }
                        }


                }
            }
        }
        return false;
    }

    public boolean canKingCapture(Pawn pawn) {
        int xPawn = pawn.xPosition;
        int yPawn = pawn.yPosition;
        int xFlag=0;
        int yFlag=0;


        for(int x = 0; x<10; x++)
        {
            for(int y = 0; y < 10; y++)
            {
                if(Math.abs(x-xPawn)==Math.abs(y-yPawn)) {
                    if (fields[x][y] != null && fields[x][y].isActive) {
                        if (!Objects.equals(fields[x][y].color, pawn.color)) {      //bicie


                            if(x > xPawn && x+1 < 10){xFlag = 1;}
                            else if(x < xPawn && x-1 >=0 ){
                                xFlag = -1;
                            }
                            if(y > yPawn && y + 1 < 10 ){yFlag = 1;}
                            else if(y < yPawn && y-1 >=0 ){
                                yFlag = -1;
                            }
                            if(xFlag != 0 && yFlag != 0 && x+xFlag<10 && x+xFlag >=0 &&y+yFlag<10 && y+yFlag >=0 && fields[x+xFlag][y+yFlag] == null && fields[x][y].isActive){
                                System.out.println((x+xFlag)+":::::"+(y+yFlag));
                                return true;
                            }


                        }
                    }


                }
            }
        }
        return false;
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
                    fields[k][i] = pawn;
                    pawnList.add(pawn);

                }
            }
        }
    }

    @Override
    public void movePawn(Pawn pawn, int x, int y) {
        if(!pawn.color.equals(this.turn))return;

        if(this.multipleCapturePawn!=null&&pawn.equals(multipleCapturePawn)){
            this.multipleCapturePawn = null;
        }
        int xPawn = pawn.xPosition;
        int yPawn = pawn.yPosition;

        String color = pawn.color;
        Move moveWithCapture = new Move(x,y,true);
        Move moveWithNoCapture = new Move(x,y,false);

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
            if(pawn.isKing){                                                //czy jest bicie wielokrotne
                if(canKingCapture(pawn)) {
                    System.out.println("TRUE");
                    multipleCapturePawn = pawn;
                }
            } else {
                if(canPawnCapture(pawn)) {
                    multipleCapturePawn = pawn;
                }
            }
            if(multipleCapturePawn == null)this.changeTurn();
        } else if(movesList.contains(moveWithNoCapture)) {
            System.out.println("BEZBICIA");
            pawn.changePosition(x,y);
            this.changeTurn();
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
    public String whichPlayerTurn() {
        return this.turn;
    }
    public List<Pawn> playerPawnWithCaptureList(String playerColor) {
        List<Pawn> list = new ArrayList<>();
       // List<Move> possibleMoves = new ArrayList<>();
        for(Pawn p:this.pawnList)
        {
            if(p.color.equals(playerColor)&&p.isActive){

            if(p.isKing){if(this.canKingCapture(p)){
                list.add(p);
            } }
            else{
                if(this.canPawnCapture(p)){
                    list.add(p);
                }
            }

            if(this.canPawnCapture(p)){
                list.add(p);
            }

            }
        }
        return list;
    }
    @Override
    public boolean checkIfWon() {
        return false;
    }

    public void changeTurn() {
        if(this.turn.equals("white")){
            this.turn = "black";
        } else {
            this.turn = "white";
        }
    }
}
