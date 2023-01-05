package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
//TODO przetestowac
public class EnglishCheckers extends GameType{


    public EnglishCheckers()
    {
        this.xSize = 8;
        this.ySize = 8;
        this.pawnCount = 12;
        this.board = new DefaultBoard(xSize, ySize, pawnCount);
        //board.setPawnList();
        //board.updateFields();
    }
    @Override
    List<Move> checkPawnPossibleMoves(Pawn pawn) {
        int xFlag = 0;
        int yFlag = 0;
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
           // for (int y = yPawn - 1; y <= yPawn + 1; y+=2) {
            int y;
            if(pawn.color.equals("white")) {
                y = yPawn +1;
            } else {
                y = yPawn - 1;
            }

                if (x >= 0 && x < this.xSize && y >= 0 && y < this.ySize) {
                    //if (board.fields[x][y] == null)
                    if (board.fields[x][y] != null &&  board.fields[x][y].isActive) {
                        if (!Objects.equals(board.fields[x][y].color, pawn.color)) {      //bicie

                            xFlag = coordsRelation(x,y,xPawn,yPawn)[0];
                            yFlag = coordsRelation(x,y,xPawn, yPawn)[1];
                            if(x+xFlag >=0 && x+xFlag < this.xSize && y+yFlag >=0 && y+yFlag < this.ySize && xFlag != 0 && yFlag != 0 && board.fields[x+xFlag][y+yFlag] == null){
                                possibleMoves.add(new Move(x + xFlag, y + yFlag, true));
                                captureExist = true;
                            }


                        }
                    } else{

                        possibleMoves.add(new Move(x, y,false));

                    }




                }
                xFlag = 0;
                yFlag = 0;
           // }
        }
        //jezeli wystapilo bicie usuwamy z listy wszystkie ruchy nie będące biciami
        if(captureExist) {

            possibleMoves.removeIf(move -> !move.isCapture());
        }
        return possibleMoves;
    }
    @Override
    List<Move> checkKingPossibleMoves(Pawn pawn) {
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

        for (int x = xPawn - 1; x <= xPawn + 1; x+=2) {
            for (int y = yPawn - 1; y <= yPawn + 1; y+=2) {
                if (x >= 0 && x <this.xSize && y >= 0 && y < this.ySize) {

                    if (board.fields[x][y] != null && board.fields[x][y].isActive) {
                        if (!Objects.equals(board.fields[x][y].color, pawn.color)) {      //bicie


                            xFlag = coordsRelation(x,y,xPawn,yPawn)[0];
                            yFlag = coordsRelation(x,y,xPawn, yPawn)[1];
                            if(x+xFlag >=0 && x+xFlag < this.xSize && y+yFlag >=0 && y+yFlag < this.ySize && xFlag != 0 && yFlag != 0 && board.fields[x+xFlag][y+yFlag] == null){
                                possibleMoves.add(new Move(x + xFlag, y + yFlag, true));
                                captureExist = true;
                            }


                        }
                    } else{
                        possibleMoves.add(new Move(x, y,false));

                    }




                }
                xFlag = 0;
                yFlag = 0;
            }
        }


        if(captureExist) {
            //System.out.println("CAPTURE");
            possibleMoves.removeIf(move -> !move.isCapture());
        }
        return possibleMoves;
    }

    @Override
    boolean canPawnCapture(Pawn pawn) {
        int xFlag = 0;
        int yFlag = 0;
        int xPawn = pawn.xPosition;
        int yPawn = pawn.yPosition;

        for (int x = xPawn - 1; x <= xPawn + 1; x+=2) {
            // for (int y = yPawn - 1; y <= yPawn + 1; y+=2) {
            int y;
            if(pawn.color.equals("white")) {
                y = yPawn +1;
            } else {
                y = yPawn - 1;
            }

            if (x >= 0 && x < 8 && y >= 0 && y < 8) {
                //if (board.fields[x][y] == null)
                if (board.fields[x][y] != null &&  board.fields[x][y].isActive) {
                    if (!Objects.equals(board.fields[x][y].color, pawn.color)) {      //bicie

                        xFlag = coordsRelation(x,y,xPawn,yPawn)[0];
                        yFlag = coordsRelation(x,y,xPawn, yPawn)[1];
                        if(x+xFlag >=0 && x+xFlag < this.xSize && y+yFlag >=0 && y+yFlag < this.ySize && xFlag != 0 && yFlag != 0 && board.fields[x+xFlag][y+yFlag] == null){
                            return true;
                        }


                    }
                }




            }
            xFlag = 0;
            yFlag = 0;
            // }
        }

        return false;
    }

    @Override
    boolean canKingCapture(Pawn pawn) {
        String color = pawn.color;
        int xPawn = pawn.xPosition;
        int yPawn = pawn.yPosition;
        int xFlag = 0;
        int yFlag = 0;

        for (int x = xPawn - 1; x <= xPawn + 1; x+=2) {
            for (int y = yPawn - 1; y <= yPawn + 1; y+=2) {
                if (x >= 0 && x < this.xSize && y >= 0 && y < this.ySize) {

                    if (board.fields[x][y] != null && board.fields[x][y].isActive) {
                        if (!Objects.equals(board.fields[x][y].color, pawn.color)) {      //bicie


                            xFlag = coordsRelation(x,y,xPawn,yPawn)[0];
                            yFlag = coordsRelation(x,y,xPawn, yPawn)[1];
                            if(x+xFlag >=0 && x+xFlag < this.xSize && y+yFlag >=0 && y+yFlag < this.ySize && xFlag != 0 && yFlag != 0 && board.fields[x+xFlag][y+yFlag] == null){
                                return true;
                            }


                        }
                    }




                }
                xFlag = 0;
                yFlag = 0;
            }
        }



        return false;
    }
}
