package org.example;
//wzorzec template
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
public abstract class GameType implements Game {
    public Board board;
    public String turn = "white";

    int xSize; //poziomo
    int ySize;  //pionowo
    int pawnCount;
    Pawn multipleCapturePawn;
    String whoWon ="";



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
    public String boardToString(){              //info o położeniu pionków na planszy, jeśli puste wszystkie pionki gracza zbite
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
                    if(p.isKing)strBlack.append("D");
                    strBlack.append(Integer.toString(p.xPosition));
                    strBlack.append(Integer.toString(p.yPosition));
                }
            }
        }
        if(strWhite.isEmpty() || !canPlayerMove("white")) {
            whoWon = "blackwon";
        } else if (strBlack.isEmpty() || !canPlayerMove("black")) {
            whoWon = "whitewon";
        }
        s=strWhite.toString()+":"+strBlack.toString();
        return s;
    }

    @Override
    public int getBoardSize() {
        return board.xSize;
    }

    @Override
    public int getPawnCount() {
        return board.pawnCount;
    }

    @Override
    public String whichPlayerTurn() {
        if(!Objects.equals(whoWon, "")){return whoWon;}
        return this.turn;
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
    int[] coordsRelation(int x, int y, int xPawn, int yPawn)
    {
        int[] flags = new int[2];
        if(x > xPawn){flags[0] = 1;}
        else if(x < xPawn  ){
            flags[0] = -1;
        }
        if(y > yPawn ){flags[1] = 1;}
        else if(y < yPawn ){
            flags[1] = -1;
        }
        return flags;
    }
    public void movePawn(Pawn pawn, int x, int y) {

        if( pawn == null || !pawn.color.equals(this.turn)  )return;

        if(this.multipleCapturePawn!=null&&pawn.equals(multipleCapturePawn)){
            this.multipleCapturePawn = null;
        }
        int xPawn = pawn.xPosition;
        int yPawn = pawn.yPosition;
        int xFlag = 0;
        int yFlag = 0;
        String color = pawn.color;
        Move moveWithCapture = new Move(x,y,true);
        Move moveWithNoCapture = new Move(x,y,false);

        List<Move> movesList;

        movesList = checkPossibleMoves(pawn);

        if(movesList.contains(moveWithCapture)) {
            //System.out.println("zBICIem");
            /*
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


            */
            xFlag = coordsRelation(x,y,xPawn,yPawn)[0];
            yFlag = coordsRelation(x,y,xPawn,yPawn)[1];

            xFlag*=-1;
            yFlag*=-1;
            board.fields[x+xFlag][y+yFlag].capture();

            pawn.changePosition(x,y);
            if(pawn.isKing){
                if(canKingCapture(pawn)) {
                    multipleCapturePawn = pawn;
                }
            } else {
                if(canPawnCapture(pawn)) {
                    multipleCapturePawn = pawn;
                }
            }
            if(multipleCapturePawn == null)this.changeTurn();
        } else if(movesList.contains(moveWithNoCapture)) {
            pawn.changePosition(x,y);
            this.changeTurn();
        }

        board.updateFields();
        checkKings();
    }
    boolean canPlayerMove(String color) {
        for(Pawn p : this.board.pawnList)
        {
            if(p.color.equals(color) && !checkPossibleMoves(p).isEmpty())return true;
        }
        return false;
    }
    void changeTurn() {
        if(this.turn.equals("white")){
            this.turn = "black";
        } else {
            this.turn = "white";
        }
    }
    void checkKings()
    {
        for(int x = 0; x < xSize; x++)
        {  try {
            if (Objects.equals(board.fields[x][0].color, "black") && (!canPawnCapture(board.fields[x][0]) || multipleCapturePawn == null)) {
                board.fields[x][0].setKing();
            }  } catch (NullPointerException e){}
            try {
                if (Objects.equals(board.fields[x][ySize-1].color, "white") && (!canPawnCapture(board.fields[x][ySize-1]) || multipleCapturePawn == null)) {
                    board.fields[x][ySize-1].setKing();
                }
            } catch (NullPointerException e){}
        }
    }
    /**Metoda sprawdzajaca mozliwe ruchy pionka
     * @param pawn pionek, ktorego ruchy sprawdzamy
     * @return lista ruchow, ktore pionek moze wykonac
     */
    abstract List<Move> checkPawnPossibleMoves(Pawn pawn);

    /**Metoda sprawdzajaca mozliwe ruchy damki
     * @param pawn pionek, ktory jest damka
     * @return lista ruchow, ktore damka moze wykonac
     */
    abstract List<Move> checkKingPossibleMoves(Pawn pawn);

    /**Metoda sprawdzajaca czy pionek ma mozliwe bicia
     * @param pawn sprawdzany pionek
     * @return true - pionek ma bicia, false - pionek nie ma bic
     */
    abstract boolean canPawnCapture(Pawn pawn);

    /**Metoda sprawdzajaca czy damka ma mozliwe bicia
     * @param pawn sprawdzana damka
     * @return true - damka ma bicia, false - damka nie ma bic
     */
    abstract boolean canKingCapture(Pawn pawn);
}
