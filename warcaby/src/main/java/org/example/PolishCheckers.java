package org.example;

import java.util.*;
import java.util.List;
//TODO remis
//TODO przerzucic czesc ogólnych metod do GameType i przygotowac do dodania kolejnych trybów
public class PolishCheckers extends GameType{
    //TODO sprawdzic czy w biciu wielokrotnym możemy zmienić pionek, którym bijemy
    public int xSize = 10; //poziomo
    public int ySize = 10;  //pionowo
    public int pawnCount = 5;




    public PolishCheckers() {
        this.board = new PolishBoard(xSize, ySize, pawnCount);
        board.setPawnList();

        board.updateFields();
    }
    List<Move> checkPawnPossibleMoves(Pawn pawn) {
        int xFlag = 0;
        int yFlag = 0;
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
        for (int x = xPawn - 1; x <= xPawn + 1; x+=2) {
            for (int y = yPawn - 1; y <= yPawn + 1; y+=2) {
                if (x >= 0 && x < 10 && y >= 0 && y < 10) {
                    //if (board.fields[x][y] == null)
                    if (board.fields[x][y] != null &&  board.fields[x][y].isActive) {
                        if (!Objects.equals(board.fields[x][y].color, pawn.color)) {      //bicie

                            /*
                            if(x > xPawn && x+1 < 10){xFlag = 1;}
                            else if(x < xPawn && x-1 >=0 ){
                                xFlag = -1;
                            }
                            if(y > yPawn && y + 1 < 10 ){yFlag = 1;}
                            else if(y < yPawn && y-1 >=0 ){
                                yFlag = -1;
                            }
                             */
                            xFlag = coordsRelation(x,y,xPawn,yPawn)[0];
                            yFlag = coordsRelation(x,y,xPawn, yPawn)[1];
                            if(x+xFlag >=0 && x+xFlag < 10 && y+yFlag >=0 && y+yFlag < 10 && xFlag != 0 && yFlag != 0 && board.fields[x+xFlag][y+yFlag] == null){
                                possibleMoves.add(new Move(x + xFlag, y + yFlag, true));
                                captureExist = true;
                            }


                        }
                    } else{
                        if(color.equals("white")){
                            if (y > yPawn) {
                                possibleMoves.add(new Move(x, y,false));

                            }
                        } else {
                            if (y < yPawn) {
                                possibleMoves.add(new Move(x, y,false));
                            }
                        }
                    }




                }
                xFlag = 0;
                yFlag = 0;
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
        int xFlag = 0;
        int yFlag = 0;
        List <Integer[]> usedDiagonals = new ArrayList<>();

        boolean captureExist = false;
        List<Move> possibleMoves = new ArrayList<>();
        if(this.multipleCapturePawn != null && !pawn.equals(multipleCapturePawn) && this.multipleCapturePawn.color.equals(pawn.color)){
            return possibleMoves;
        }
        if(!this.playerPawnWithCaptureList(pawn.color).isEmpty() && !this.playerPawnWithCaptureList(pawn.color).contains(pawn)){
            return possibleMoves;
        }


        for(int xIncrement = -1; xIncrement<=1; xIncrement+=2)          //iteracja po przekątnych od damki
        {
            for(int yIncrement = -1; yIncrement<=1; yIncrement+=2)
            {
                for(int x = xPawn, y = yPawn; x >= 0 && x<10 && y >= 0&& y<10; x+=xIncrement, y+=yIncrement)
                {
                    if (board.fields[x][y] != null && board.fields[x][y].isActive) {                    //czy trafilismy na niepuste pole
                        if (!Objects.equals(board.fields[x][y].color, pawn.color)) {         //jezeli pionek innego koloru to mamy bicie
                           /*
                            if(x > xPawn && x + 1 < 10){xFlag = 1;}
                            else if(x < xPawn && x-1 >=0 ){
                                xFlag = -1;
                            }
                            if(y > yPawn && y + 1 < 10 ){yFlag = 1;}
                            else if(y < yPawn && y-1 >=0 ){
                                yFlag = -1;
                            }
                            */
                            xFlag = coordsRelation(x,y,xPawn,yPawn)[0];
                            yFlag = coordsRelation(x,y,xPawn, yPawn)[1];
                            //System.out.println("x"+x+"y"+y+"xFlag:"+xFlag+"YFlag:"+yFlag);        //TODO sprawdzic czy mozemy bic jezeli za pionkiem byl inny zbity pionek
                            if( x+xFlag >=0 && x+xFlag < 10 && y+yFlag >=0 && y+yFlag < 10 && xFlag != 0 && yFlag != 0 && (board.fields[x+xFlag][y+yFlag] == null || !board.fields[x+xFlag][y+yFlag].isActive)){


                                    possibleMoves.add(new Move(x + xFlag, y + yFlag, true));
                                    captureExist = true;

                            }
                            break;
                        } else {
                            if(x!=xPawn && y!= yPawn)break;                              //jezeli trafilismy na pionek naszego koloru przechodzimy do innej przekatnej
                        }
                    } else {
                        possibleMoves.add(new Move(x, y, false));               //jezeli trafilismy na puste pole, dodajemy je

                    }
                    xFlag = 0;
                    yFlag = 0;
                }
            }
        }

        if(captureExist) {
            //System.out.println("CAPTURE");
            possibleMoves.removeIf(move -> !move.isCapture());
        }
        return possibleMoves;
    }
/**
    public List<Move> checkPossibleMoves(Pawn p) {
        if(p.isKing){
            return checkKingPossibleMoves(p);
        } else {
            return checkPawnPossibleMoves(p);
        }
    }
 */
    public boolean canPawnCapture(Pawn pawn) {
        int xPawn = pawn.xPosition;
        int yPawn = pawn.yPosition;
        int xFlag=0;
        int yFlag=0;


        for (int x = xPawn - 1; x <= xPawn +1; x+=2) {
            for (int y = yPawn -1 ; y <= yPawn +1; y+=2) {
                if (x >= 0 && x < 10 && y >= 0 && y < 10) {
                        if (board.fields[x][y] != null  && board.fields[x][y].isActive) {
                            if (!Objects.equals(board.fields[x][y].color, pawn.color)) {      //bicie

                                /*
                                if(x > xPawn && x+1 < 10){xFlag = 1;}
                                else if(x < xPawn && x-1 >=0 ){
                                    xFlag = -1;
                                }
                                if(y > yPawn && y + 1 < 10 ){yFlag = 1;}
                                else if(y < yPawn && y-1 >=0 ){
                                    yFlag = -1;
                                }
                                */
                                xFlag = coordsRelation(x,y,xPawn,yPawn)[0];
                                yFlag = coordsRelation(x,y,xPawn, yPawn)[1];
                                if(x+xFlag >=0 && x+xFlag < 10 && y+yFlag >=0 && y+yFlag < 10 &&xFlag != 0 && yFlag != 0 && board.fields[x+xFlag][y+yFlag] == null){
                                    return true;
                                }


                            }
                        }


                }
                 xFlag=0;
                 yFlag=0;
            }
        }
        return false;
    }

    boolean canKingCapture(Pawn pawn) {
        int xPawn = pawn.xPosition;
        int yPawn = pawn.yPosition;
        int xFlag=0;
        int yFlag=0;

        for(int xIncrement = -1; xIncrement<=1; xIncrement+=2)          //iteracja po przekątnych od damki
        {
            for(int yIncrement = -1; yIncrement<=1; yIncrement+=2)
            {
                for(int x = xPawn, y = yPawn; x >= 0 && x<10 && y >= 0&& y<10; x+=xIncrement, y+=yIncrement)
                {
                    if (board.fields[x][y] != null && board.fields[x][y].isActive) {                    //czy trafilismy na niepuste pole
                        if (!Objects.equals(board.fields[x][y].color, pawn.color)) {         //jezeli pionek innego koloru to mamy bicie

                            /*
                            if(x > xPawn && x + 1 < 10){xFlag = 1;}
                            else if(x < xPawn && x-1 >=0 ){
                                xFlag = -1;
                            }
                            if(y > yPawn && y + 1 < 10 ){yFlag = 1;}
                            else if(y < yPawn && y-1 >=0 ){
                                yFlag = -1;
                            }
                            */
                            xFlag = coordsRelation(x,y,xPawn,yPawn)[0];
                            yFlag = coordsRelation(x,y,xPawn, yPawn)[1];
                            //System.out.println("x"+x+"y"+y+"xFlag:"+xFlag+"YFlag:"+yFlag);        //TODO sprawdzic czy mozemy bic jezeli za pionkiem byl inny zbity pionek
                            if(x+xFlag >=0 && x+xFlag < 10 && y+yFlag >=0 && y+yFlag < 10 && xFlag != 0 && yFlag != 0 && (board.fields[x+xFlag][y+yFlag] == null || !board.fields[x+xFlag][y+yFlag].isActive)){


                                return true;
                            }
                            break;
                        } else {
                            if(x!=xPawn && y!= yPawn)break;                              //jezeli trafilismy na pionek naszego koloru przechodzimy do innej przekatnej
                        }
                    }
                    xFlag = 0;
                    yFlag = 0;
                }
            }
        }


        return false;

    }









}
