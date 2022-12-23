package org.example;

import java.util.*;
import java.util.List;

public class PolishCheckers extends GameType{
    public int xSize = 10; //poziomo
    public int ySize = 10;  //pionowo
    public int pawnCount = 40;
   // public Board board;

    //private List<Pawn> pawnList = new ArrayList<>();
    private String turn = "white";
    private Pawn multipleCapturePawn;

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
        for (int x = xPawn - 1; x <= xPawn + 1; x+=2) {                     //TODO ujednolic i zmniejszyc liczbe ifów
            for (int y = yPawn - 1; y <= yPawn + 1; y+=2) {
                if (x >= 0 && x < 10 && y >= 0 && y < 10) {
                    //if (board.fields[x][y] == null)
                    if (board.fields[x][y] != null &&  board.fields[x][y].isActive) {
                        if (!Objects.equals(board.fields[x][y].color, pawn.color)) {      //bicie


                            if(x > xPawn && x+1 < 10){xFlag = 1;}
                            else if(x < xPawn && x-1 >=0 ){
                                xFlag = -1;
                            }
                            if(y > yPawn && y + 1 < 10 ){yFlag = 1;}
                            else if(y < yPawn && y-1 >=0 ){
                                yFlag = -1;
                            }
                            if(xFlag != 0 && yFlag != 0 && board.fields[x+xFlag][y+yFlag] == null){
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
                    if (board.fields[x][y] != null && board.fields[x][y].isActive) {
                        if (!Objects.equals(board.fields[x][y].color, pawn.color)) {         //bicie

                            if(x > xPawn && x + 1 < 10){xFlag = 1;}
                            else if(x < xPawn && x-1 >=0 ){
                                xFlag = -1;
                            }
                            if(y > yPawn && y + 1 < 10 ){yFlag = 1;}
                            else if(y < yPawn && y-1 >=0 ){
                                yFlag = -1;
                            }
                            //System.out.println("x"+x+"y"+y+"xFlag:"+xFlag+"YFlag:"+yFlag);
                            if(xFlag != 0 && yFlag != 0 && board.fields[x+xFlag][y+yFlag] == null){

                                possibleMoves.add(new Move(x + xFlag, y + yFlag, true));
                                captureExist = true;
                            }

                        }
                    } else {
                        //System.out.println("ASDASD");
                            possibleMoves.add(new Move(x, y, false));

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


                                if(x > xPawn && x+1 < 10){xFlag = 1;}
                                else if(x < xPawn && x-1 >=0 ){
                                    xFlag = -1;
                                }
                                if(y > yPawn && y + 1 < 10 ){yFlag = 1;}
                                else if(y < yPawn && y-1 >=0 ){
                                    yFlag = -1;
                                }
                                if(xFlag != 0 && yFlag != 0 && board.fields[x+xFlag][y+yFlag] == null){
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


        for(int x = 0; x<10; x++)
        {
            for(int y = 0; y < 10; y++)
            {
                if(Math.abs(x-xPawn)==Math.abs(y-yPawn)) {
                    if (board.fields[x][y] != null && board.fields[x][y].isActive) {
                        if (!Objects.equals(board.fields[x][y].color, pawn.color)) {      //bicie


                            if(x > xPawn && x+1 < 10){xFlag = 1;}
                            else if(x < xPawn && x-1 >=0 ){
                                xFlag = -1;
                            }
                            if(y > yPawn && y + 1 < 10 ){yFlag = 1;}
                            else if(y < yPawn && y-1 >=0 ){
                                yFlag = -1;
                            }
                            if(xFlag != 0 && yFlag != 0 && x+xFlag<10 && x+xFlag >=0 &&y+yFlag<10 && y+yFlag >=0 && board.fields[x+xFlag][y+yFlag] == null && board.fields[x][y].isActive){
                                System.out.println((x+xFlag)+":::::"+(y+yFlag));
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
    /**
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


     public boolean canCapture(Pawn pawn) {
        if(pawn.isKing){
            return canKingCapture(pawn);
            }
         else {
                return canPawnCapture(pawn);
            }
    }
     */
    public void movePawn(Pawn pawn, int x, int y) {

        if( pawn == null || !pawn.color.equals(this.turn)  )return;

        if(this.multipleCapturePawn!=null&&pawn.equals(multipleCapturePawn)){
            this.multipleCapturePawn = null;
        }
        int xPawn = pawn.xPosition;
        int yPawn = pawn.yPosition;

        String color = pawn.color;
        Move moveWithCapture = new Move(x,y,true);
        Move moveWithNoCapture = new Move(x,y,false);

        List<Move> movesList;

        movesList = checkPossibleMoves(pawn);

        if(movesList.contains(moveWithCapture)) {
            //System.out.println("zBICIem");
            if(x>xPawn){
                if(y>yPawn){
                    board.fields[x-1][y-1].capture();

                } else {
                    board.fields[x-1][y+1].capture();

                }
            } else if (x<xPawn) {
                if(y>yPawn){
                    board.fields[x+1][y-1].capture();

                } else {
                    board.fields[x+1][y+1].capture();

                }
            }
            pawn.changePosition(x,y);
            if(pawn.isKing){
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
           // System.out.println("BEZBICIA");
            pawn.changePosition(x,y);
            this.changeTurn();
        }

        board.updateFields();
        checkKings();
    }

    public void checkKings()
    {
        for(int x = 0; x < 10; x++)
        {  try {
            if (Objects.equals(board.fields[x][0].color, "black")) {
                board.fields[x][0].setKing();
            }
            if (Objects.equals(board.fields[x][9].color, "white")) {
                board.fields[x][9].setKing();
            }
        } catch (NullPointerException e){}
        }
    }
    public String whichPlayerTurn() {
        return this.turn;
    }
    /**
    public List<Pawn> playerPawnWithCaptureList(String playerColor) {
        List<Pawn> list = new ArrayList<>();
       // List<Move> possibleMoves = new ArrayList<>();
        for(Pawn p:board.pawnList)
        {
            if(p.color.equals(playerColor)&&p.isActive){

            if(p.isKing){
                if(this.canKingCapture(p)){
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
*/
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
