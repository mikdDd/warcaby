package org.example;

import java.util.ArrayList;
import java.util.List;

public abstract class GameType implements GameController {
    public Board board;

    @Override
    public abstract void movePawn(Pawn pawn, int x, int y);

    public List<Move> checkPossibleMoves(Pawn pawn) {
        if(pawn.isKing){
            return checkKingPossibleMoves(pawn);
        } else {
            return checkPawnPossibleMoves(pawn);
        }
    }

    @Override
    public boolean canCapture(Pawn pawn) {
        if(pawn.isKing){
            return canKingCapture(pawn);
        }
        else {
            return canPawnCapture(pawn);
        }
    }

     List<Pawn> playerPawnWithCaptureList(String playerColor) {
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
     public Pawn getPawn(int x, int y) {
        return board.fields[x][y];
     }
    public String boardToString(){
        StringBuilder strWhite = new StringBuilder("");
        StringBuilder strBlack = new StringBuilder("");
        String s="";
        for(Pawn p: board.pawnList)
        {
            if(p.isActive){
                if(p.color.equals("white")) {
                    if(p.isKing)strWhite.append("D");
                    strWhite.append(Integer.toString(p.xPosition));
                    strWhite.append(Integer.toString(p.yPosition));
                } else {
                    if(p.isKing)strWhite.append("D");
                    strBlack.append(Integer.toString(p.xPosition));
                    strBlack.append(Integer.toString(p.yPosition));
                }
            }
        }
        s=strWhite.toString()+":"+strBlack.toString();
        return s;
    }
    public String possibleMovesToString(Pawn pawn){
        List<Move> moves= new ArrayList<>();
        StringBuilder str= new StringBuilder();
        try {

            moves = checkPossibleMoves(pawn);
        } catch (NullPointerException e){}
        for(Move p : moves)
        {

                str.append(p.getX());
                str.append(p.getY());


        }
        return str.toString();
    }
    abstract List<Move> checkPawnPossibleMoves(Pawn pawn);
    abstract List<Move> checkKingPossibleMoves(Pawn pawn);
    abstract void checkKings();
    abstract boolean canPawnCapture(Pawn pawn);
    abstract boolean canKingCapture(Pawn pawn);
}
